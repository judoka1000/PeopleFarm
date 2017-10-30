var app = angular.module('PeopleApp', []);
app.controller('PeopleCtrl', PeopleCtrl);

function PeopleCtrl($scope,$http,$document,$interval,$timeout,apiEngine,personsFactory){
    $scope.init = function(){
        //$interval($scope.updateGamestate, 5000);
    }

    apiEngine.people( function (response) {
        $scope.persons = personsFactory.addPersons(response.data.people);
    });

    $scope.cursor = "";
    $scope.clickAction = "";

    $scope.updateGamestate = function(){
        console.log("updategame");
        var persons = personsFactory.getPersons();

        for (key in persons) {
            persons[key].updateStatus();
            console.log("person:");
            console.log(persons[key]);
        }
    }

    $scope.personClicked = function(person){
        console.log("Person " + person.id + " clicked");
        switch($scope.clickAction) {

            case "eat":
                console.log("Starting to eat, njam njam njam");
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
                var audio = new Audio('sounds/screem.mp3');
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

    $scope.setClickAction = function(action){
        $scope.cursor = action + "Cur";
        $scope.clickAction = action;
        console.log("ready to " + action);
    }

    $scope.actionUpdate = function(){
        apiEngine.reset(function(){});
    }

    $scope.actionNone = function(){
        console.log("actionNone");
        $scope.cursor = "";
    }
    
    $scope.init();
}