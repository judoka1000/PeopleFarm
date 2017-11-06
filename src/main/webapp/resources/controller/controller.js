var app = angular.module('PeopleApp', []);
app.controller('PeopleCtrl', PeopleCtrl);

function PeopleCtrl($scope,$http,$document,$interval,$timeout,apiEngine,personsFactory){
    $scope.init = function(){
        $interval($scope.updateGamestate, 2000);
    }

    $scope.initializePeople = function() {
        apiEngine.people( function (response) {
            $scope.persons = personsFactory.addPersons(response.data);
        });
    };
    $scope.initializePeople();

    (function(){
        apiEngine.getPlayername(function(response) {
            $scope.playername = response.data.name;
        });
    })();

    $scope.tiles = (function(){
        var returnvalue = new Array(8);
        for(i = 0; i < 8; i++) {
            returnvalue[i] = new Array(8);
            for(j = 0; j < 8; j++) {
                returnvalue[i][j] = {x: i, y: j, type: null};
            }
        }
        return returnvalue;
    })();

    $scope.updateRoomGrid = function() {
        for(i = 0; i < 8; i++) {
            for(j = 0; j < 8; j++) {
                $scope.tiles[i][j].type = null;
            }
        }
        angular.forEach($scope.persons, function(value, key) {
            $scope.tiles[value.getPosition().x][value.getPosition().y].type = 'person';
            $scope.tiles[value.getPosition().x][value.getPosition().y].id = value.id;
        });
    }

    $scope.$watchCollection('persons', $scope.updateRoomGrid);

    $scope.cursor = "";
    $scope.clickAction = "";
    $scope.showPeopleId = -1;
    $scope.score = 0;
    $scope.person2 = "";
    
    $scope.updateGamestate = function(){
        console.log("updategame");
        var persons = personsFactory.getPersons();
        for (key in persons) {
            persons[key].getStatus();
            persons[key].move();
            $scope.updateRoomGrid();
        }
        
        apiEngine.getScore(function(response){
        	$scope.score = response.data;
        },
        function(response){
            if($scope.startingGame == false) {
                console.log("No session on server. Starting new game.");
                $scope.newGameAction();
                $scope.startingGame = true;
            }
        });
    }

    $scope.personClicked = function(person){
        console.log("Person " + person.id + " clicked");
        switch($scope.clickAction) {

            case "eatHamburger":
                console.log("Starting to eat, njam njam njam");
                person.eat("hamburger");
            break;

            case "eatDogfood":
                console.log("Starting to eat dogfood, woef");
                person.eat("dogfood");
                break;

            case "sleep":
                console.log("zzz zzz zzz");
                person.sleep();
                break;

            case "reproduce":
                if($scope.person2 == ""){
                    console.log("1 person selected");
                    person.reproducing = true;
                    $scope.person2 = person;
                }
                else {
                    console.log("2 persons selected, reproducing...");
                    person.reproducing = true;
                    person.reproduce($scope.person2);
                    apiEngine.personSetTwoTask(person.id,$scope.person2.id,"reproducing",function(response){
                        person.reproducing = false;
                        $scope.person2.reproducing = false;
                        person.childId = response.data.id;
                        console.log("in person");
                        console.log(person.childId);
                        apiEngine.personStatus(person.childId,function(response){
                            console.log(response.data);
                            personsFactory.addPerson(response.data);
                            $scope.updateGamestate();
                            $scope.person2 = "";
                        });
                    });
                }
                break;

            case "kill":
                person.die();
            break;

            case "test":
                console.log("test");
                person.status.currentCaptchas += 1;
            break;

            case "info":
                console.log("Requesting Info");
                $scope.showPeopleId = person.id;
            break;

            case "collect":
                apiEngine.personSettask(person.id,"collecting",function(response){
                    person.status.currentCaptchas = 0;
                });
            break;

            default:
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
        apiEngine.newGame(function(){$scope.initializePeople();$scope.startingGame=false;});
        $scope.updateGamestate();
    };

    $scope.renamePlayer = function(newName) {
        apiEngine.renamePlayer(newName, function(){});
    }

    $scope.init();
}