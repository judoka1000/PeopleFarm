

app.factory('personsFactory', ['apiEngine','$timeout',
    function(apiEngine,$timeout) {
    var persons = {};
    class Person{
        constructor(person){
            this.setFields(person);
            this.visible = true;
            this.selectedToReproduce = false; // when true, sprite has heart icon
            this.heartAnimation = false;
            this.destinationX = null;
            this.destinationY = null;
            this.x = Math.floor(Math.random() * 8);
            this.y = Math.floor(Math.random() * 8);
            this.thought = null;
        }

        setFields(person){
            if (typeof this.id == 'undefined'){
                this.status = person.status;
            } else {
                var obj = this;
                angular.forEach(person.status, function(value, key) {
                    obj.status[key] = value;
                });
            }
            this.id = person.id;
            this.gender = person.gender.toLowerCase();
            this.fullGender = person.gender.toLowerCase();
            this.abilities = person.abilities;
            this.sprite = person.sprite;

        }

        setCurrentCaptchas(add){
            this.status.currentCaptchas += add;
        }

        getStatus(){
            var obj = this;
            var oldCollectedCaptchas = this.status.currentCaptchas;
            apiEngine.personStatus(this.id, function(response){

                obj.setFields(response.data.data);
                if(obj.status.health=="DEAD"){
                    obj.die();
                }
                if(obj.status.hunger < 34) {
                    obj.thought = "Ik heb honger";
                } else if(obj.status.tiredness < 34) {
                    obj.thought = "Ik ben moe";
                } else {
                    obj.thought = null;
                }
                if(oldCollectedCaptchas != obj.status.currentCaptchas){
                    obj.status.captchaChange = "newCaptchas";
                    $timeout(function(person){person.status.captchaChange="";},300,true,obj);
                    var audio = new Audio('resources/sounds/coin.mp3');
                    audio.play();
                }
            },
            //on error
            function(response){
                if(response.status == 404){
                    console.log(obj.id + " not found. Remove.");
                    obj.remove();
                }
            });
        }

        getAdult(){
            if(this.status.age >= 18) {
                return "Adult";
            } else {
                return "Child";
            }
        }

        move() {
            // Moving to partner
            if(this.destinationX != null && this.destinationY != null){
                // Not yet arrived
                if(this.x != this.destinationX || this.y != this.destinationY){
                    this.x = this.destinationX;
                    this.y = this.destinationY;
                // Arrived
                } else {
                    if  (!this.heartAnimation){
                        this.heartAnimation = "heartAnimation";
                    } else {
                        this.selectedToReproduce = false;
                        this.heartAnimation = false;
                        this.destinationX = null;
                        this.destinationY = null;
                    }
                }
            } else {
                var sw = Math.floor(Math.random() * 4);
                switch(sw) {
                    case 0:
                        this.x = this.x - 1;
                        if(this.x < 0) this.x = 0;
                        break;
                    case 1:
                        this.x = this.x + 1;
                        if(this.x > 7) this.x = 7;
                        break;
                    case 2:
                        this.y = this.y - 1;
                        if(this.y < 0) this.y = 0;
                        break;
                    case 3:
                        this.y = this.y + 1;
                        if(this.y > 7) this.y = 7;
                        break;
                }
            }
        }

        getPosition() {
            return {
                x: this.x,
                y: this.y
            }
        }

        getCanReproduce(partner,clickAction) {
            if(!this.canReproduce(partner,clickAction)){
                return "cannotReproduce";
            } else {
                return "";
            }
        }

        canReproduce(partner,clickAction){
            var actionIsReproduce = clickAction === "reproduce";
            var isChild = this.getAdult() != "Adult";
            var partnerHasSameGender = partner != null && partner.gender === this.gender;

            if(actionIsReproduce){
                if(isChild || partnerHasSameGender){
                    return false
                } else {
                    return true;
                }
            } else {
                return true;
            }
        }

        eat(food){
            var obj = this;
            console.log("eating" + food);
            apiEngine.personSettask(this.id,"eating" + food,function(response){
                obj.getStatus();
            });
        }

        sleep(amount=10){
            var obj = this;
            apiEngine.personSettask(this.id,"sleep",function(response){
                obj.getStatus();
            });
        }

        remove(){
            this.visible = false;
            delete persons[this.id];
        }

        die(){
            apiEngine.delete(this.id,function(response){

            });
            this.sprite = "Tombstone";
            $timeout(function(obj, persons){obj.remove();},3500,true,this,persons);
            var audio = new Audio('resources/sounds/screem.mp3');
            audio.play();

        }
    }



    return {
        addPerson: function(person){
            persons[person.id] = new Person(person);
            return persons;
        },
        addPersons: function (_persons){
            //var index;
            angular.forEach(_persons, function(value, key) {
                persons[_persons[key].id] = new Person(_persons[key]);
            });
            return persons;
        },
        getPerson: function(id){
            return persons[id];
        },
        getPersons: function() {
            return persons;
        }
    };
}]);