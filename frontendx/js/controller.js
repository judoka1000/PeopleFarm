var app = angular.module('PeopleApp', []);
app.controller('PeopleCtrl', PeopleCtrl);

function PeopleCtrl($scope,$http,$document,$interval,$timeout,apiEngine,personsFactory){
    apiEngine.people( function (response) {
        //$scope.people = response.data.people;
        //personsFactory.addPerson($scope.people[0]);
        //console.log(personsFactory.getPerson(0));
        //var p1 = new Person($scope.people[0]);
        $scope.people2 = personsFactory.addPersons(response.data.people);
        //console.log(personsFactory.getPerson(3));
        //personsFactory.getPerson(3).update();
    });
    $scope.cursor = "";
    $scope.clickAction = "";
    $scope.init = function(){

        //document.getElementById("percBar_1").innerHTML("elo");
        //angular.element($document[0].querySelector("div#percBar_1")).css('width','10%');
        $interval($scope.updateGamestate, 5000);
    }

    $scope.updateGamestate = function(){
        console.log("updategame");
        var persons = personsFactory.getPersons();
        //console.log(personsFactory.getPersons());
        console.log(persons.length);

        for (key in persons) {
            persons[key].updateStatus();
            console.log("person:");
            console.log(persons[key]);
        }
            /*
            for(var i=0; i<tPeople.length; i++){

                $scope.people[i].status.hungry = response.data.people[i].status.hungry;
                $scope.people[i].status.tiredness = response.data.people[i].status.tiredness;
            }
            */
            //$scope.people = tPeople;
        //});
    }



    $scope.getPerson = function(id){
        apiEngine.status(id, function (response) {
            console.log(response);
            alert(response.data.gender);
        });
    }

    $scope.getTask = function(person){
        switch($scope.clickAction) {

            case "feed":
                console.log("Njam njam njam");
                person.eat();
            break;

            case "sleep":
                console.log("zzz zzz zzz");
                person.sleep();
                break;

            case "kill":
                console.log("Kill");
                person.sprite = "Tombstone";
                $timeout(function(){$scope.removePeople(person)},3500);
                var audio = new Audio('/screem.mp3');
                audio.play();
            break;

            case "update":
                console.log("reset");
                person.update();
            break;


            default:
                apiEngine.people(function (response) {
                    //console.log(response.data.people);
                    //$scope.people = response.data.people;
                    tPeople = response.data.people;
                    for (key of Object.keys(tPeople)) {
                        for (key2 of Object.keys(tPeople[key])) {
                            //console.log(key, tPeople[key]);
                            $scope.people[key][key2] = tPeople[key][key2];
                        }

                    }
                });
        }

    }

    $scope.removePeople = function(person){
        person.visible=false;
    }

    $scope.getBarPc = function(val,reverse=false,min=0,max=100){
        var result = "";
        var tOkeLow = max*0.33;
        var tOkeHigh = max*0.66;
        if(val > min & val <= tOkeLow){
            result = reverse ? "good" : "danger";
        }

        if(val > tOkeLow & val <= tOkeHigh){
            result = "oke";
        }

        if(val > tOkeHigh & val <= max){
            result = reverse ? "danger" : "good";
        }

        return result;
    }

    $scope.actionEating = function(){
        //angular.element(document).find('body').addClass('hamburgerCur');
        $scope.cursor = "hamburgerCur";
        $scope.clickAction = "feed";
        console.log("ready to eat");
    }

    $scope.actionSleeping = function(){
        $scope.cursor = "sleepingCur";
        $scope.clickAction = "sleep";
        console.log("ready to sleep");
    }

    $scope.actionKill = function(){
        $scope.cursor = "killCur";
        $scope.clickAction = "kill";
        console.log("ready to kill");
    }

    $scope.actionUpdate = function(){
        apiEngine.reset(function(){});
    }

    $scope.actionNone = function(){
        console.log("actionNone");
        //angular.element(document).find('body').removeClass();
        $scope.cursor = "";
    }

    $scope.verandering = function(person){
        console.log("Verandering: " + person.id);
    }

    $scope.getVisible = function(person){
        if(person.visible != false){
            return true;
        } else {
            return false;
        }
    }

    $scope.init();


}



// app.directive("dirpeople", function() {
//     return {
//         restrict: 'A',
//         link: function (scope, elem, attrs, ctrl) {
//             //elem.attr('style','width: 200px');
//             elem.css('width', '200px');
//         }
//     };
// });
