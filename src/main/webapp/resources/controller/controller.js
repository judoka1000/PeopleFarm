var app = angular.module('PeopleApp', ['ngCookies', 'ngClickCopy']);
app.controller('PeopleCtrl', PeopleCtrl);

function PeopleCtrl($scope,$http,$document,$interval,$timeout,apiEngine,personsFactory){
    $scope.init = function(){
        $interval($scope.updateGamestate, 2000);
    }

    $scope.initializePeople = function() {
        apiEngine.people( function (response) {
            $scope.persons = personsFactory.addPersons(response.data.data);
        });
    };
    $scope.initializePeople();

    (function(){
        apiEngine.getPlayername(function(response) {
            $scope.playername = response.data.data.name;
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

    $scope.cursor = "";
    $scope.clickAction = "";
    $scope.showPeopleId = -1;
    $scope.score = 0;
    $scope.personSelected = null;
    $scope.newPerson = null;
    
    $scope.updateGamestate = function(){
        console.log("updategame");
        if ($scope.newPerson != null) {
            personsFactory.addPerson($scope.newPerson);
            $scope.newPerson = null;
        }

        var persons = personsFactory.getPersons();
        for (key in persons) {
            persons[key].getStatus();
            persons[key].move();
        }
        
        apiEngine.getScore(function(response){
        	$scope.score = response.data.data;
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
                // Select first person
                if($scope.personSelected == null){
                    console.log("1 person selected");
                    person.selectedToReproduce = true;
                    $scope.personSelected = person;
                }
                // Select second person
                else if ($scope.newPerson == null){
                    console.log("2 persons selected, reproducing...");
                    person.selectedToReproduce = true;
                    // Do put
                    apiEngine.personSetTwoTask(person.id,$scope.personSelected.id,"reproducing",function(response){
                        person.childId = response.data.id;
                        // Get new person
                        apiEngine.personStatus(person.childId,function(response){
                            $scope.movePeople($scope.personSelected,person);
                            $scope.newPerson = response.data; // store the new person so that he/she can be added to the view in update()
                            $scope.personSelected = null;
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

    $scope.movePeople = function(person1,person2){
        // Move persons towards each other
        var desX = Math.round((person1.x + person2.x)/2);
        var desY = Math.round((person1.y + person2.y)/2);
        person1.destinationX = desX;
        person1.destinationY = desY;
        person2.destinationX = desX;
        person2.destinationY = desY;
        $scope.updateGamestate();
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