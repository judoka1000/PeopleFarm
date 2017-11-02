

app.factory('personsFactory', ['apiEngine','$timeout',
    function(apiEngine,$timeout) {
    var persons = {};
    class Person{
        constructor(person){
            this.setFields(person);
            this.visible = true;
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
                obj.setFields(response.data);
                if(obj.status.health=="DEAD"){
                    obj.die();
                }
                if(oldCollectedCaptchas != obj.status.currentCaptchas){
                    obj.status.captchaChange = "newCaptchas";
                    $timeout(function(person){person.status.captchaChange="";},300,true,obj);
                    var audio = new Audio('resources/sounds/coin.mp3');
                    audio.play();
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

        eat(amount=10){
            var obj = this;
            apiEngine.personSettask(this.id,"eating",function(response){
                obj.getStatus();
            });
        }

        sleep(amount=10){
            var obj = this;
            apiEngine.personSettask(this.id,"sleeping",function(response){
                obj.getStatus();
            });
        }

        reproduce(person){
            var obj = this;
            apiEngine.personSetTwoTask(this.id,person.id,"reproducing",function(response){
                obj.getStatus();
            });
        }

        remove(){
            this.visible = false;
        }

        die(){
            apiEngine.delete(this.id,function(response){

            });
            this.sprite = "Tombstone";
            $timeout(function(obj, persons){delete persons[obj.id];},3500,true,this,persons);
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