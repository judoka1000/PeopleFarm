var app = angular.module('PeopleApp', []);
app.controller('PeopleCtrl', PeopleCtrl);

function PeopleCtrl($scope,$http,$document,$interval,$timeout,apiEngine,personsFactory){
    $scope.init = function(){
        $interval($scope.updateGamestate, 10000);
    }

    $scope.initializePeople = function() {
        apiEngine.people( function (response) {
            console.log(response);
            $scope.persons = personsFactory.addPersons(response.data);
            //$scope.persons = personsFactory.addPersons(response.data);
    })};
    $scope.initializePeople();

    $scope.cursor = "";
    $scope.clickAction = "";
    $scope.showPeopleId = -1;
    $scope.score = 0;
    
    $scope.updateGamestate = function(){
        console.log("updategame");
        var persons = personsFactory.getPersons();

        for (key in persons) {
            persons[key].getStatus();
            console.log("person:");
            console.log(persons[key]);
        }
        
        apiEngine.getScore(function(response){
        	$scope.score = response.data;
        });
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
                person.die();
            break;

            case "test":
                console.log("test");
                person.status.currentCaptchas += 1;2
            break;

            case "info":
                console.log("Requesting Info");
                $scope.showPeopleId = person.id;
            break;

            default:
                apiEngine.people(function (response) {
                    tPeople = response.data;
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

    $scope.actionUpdate = function(person){
        console.log("change on person " + person.id);
    }

    $scope.actionTest = function(person){

    }

    $scope.actionNone = function(){
        console.log("actionNone");
        $scope.cursor = "";
        $scope.clickAction = "none";
    }

    $scope.newGameAction = function() {
        apiEngine.newGame(function(){$scope.initializePeople();});
    };

    $scope.init();
}