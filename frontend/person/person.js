

app.factory('personsFactory', ['apiEngine',
    function(apiEngine) {

    class Person{
        constructor(person){
            this.setFields(person);
            this.visible = true;
        }

        setFields(person){
            if (typeof this.id == 'undefined'){
                this.status = person.status;
            } else {
                for (var key of Object.keys(person.status)) {
                    this.status[key] = person.status[key];
                }
            }
            this.id = person.id;
            this.gender = person.gender;
            this.fullGender = person.gender == "m" ? "male" : "female";
            this.abilities = person.abilities;
            this.sprite = person.sprite;
        }

        getStatus(){
            console.log("getstatus");
            var obj = this;
            apiEngine.status(this.id, function(response){
                console.log("response: ");
                console.log(response.data);
                obj.setFields(response.data);
            });
        }

        updateStatus(){
            console.log("updateStatus");
            var obj = this;
            apiEngine.updateStatus(this.id, function(response){
                console.log("response: ");
                console.log(response.data);
                obj.setFields(response.data);
            });
        }

        eat(amount=10){
            var obj = this;
            apiEngine.setTask(this.id,"eating",function(response){
                obj.getStatus();
            });
        }

        sleep(amount=10){
            var obj = this;
            apiEngine.setTask(this.id,"sleeping",function(response){
                obj.getStatus();
            });
        }
    }

    var persons = {};

    return {
        addPerson: function(person){
            //var index = !persons.length ? 0 : persons.length;
            //var test = apiEngine.people(function(response){});
            //console.log("test: " + test);
            persons[person.id] = new Person(person);
            return persons;
        },
        addPersons: function (_persons){
            //var index;
            for (key of Object.keys(_persons)) {
                //index = !persons.length ? 0 : persons.length;
                persons[_persons[key].id] = new Person(_persons[key]);
                console.log("Index " + _persons[key].id + " " + persons[_persons[key].id]);
            }
            return persons;
        },
        getPerson: function(id){
            return persons[id];
        },
        getPersons: function(){
            return persons;
        }
    };
}]);