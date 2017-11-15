app.controller('PeopleCtrl', PeopleCtrl);

function PeopleCtrl($scope,$http,$document,$interval,$timeout,$window,apiEngine,personsFactory,gameEngine){
    $scope.init = function(){
        apiEngine.getButtons(function(response){
            $scope.buttons = response.data.data;
            //console.log(buttons.data);
            }
        );

        apiEngine.getShopButtons(function(response){
            $scope.storeButtons = response.data.data;
        });

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
    $scope.cp1 = -1;
    $scope.cp2 = -1;
    $scope.score = 0;
    $scope.personSelected = null;
    $scope.newPerson = null;
    $scope.selectedCol = 0;

    $scope.updateGamestate = function(){
        if(!gameEngine.getAPI()) {
            console.log("API disabled");
            return;
        }

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
            if(this.sess)
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

            case "eatingHamburger":
                console.log("Starting to eat, njam njam njam");
                person.eat("hamburger");
            break;

            case "eatingDogfood":
                console.log("Starting to eat dogfood, woef");
                person.eat("dogfood");
                break;

            case "eatingRedbull":
                console.log("Starting to drink Red Bull");
                person.eat("redbull");
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
                        // Get new person
                        $scope.movePeople($scope.personSelected,person);
                        $scope.newPerson = response.data.data; // store the new person so that he/she can be added to the view in update()
                        $scope.personSelected = null;
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
                $scope.cp1 = -1;
                $scope.cp2 = -1;
                $scope.showPeopleId = person.id;
            break;

            case "compare":
                $scope.showPeopleId = -1; // disable info panel
                if($scope.cp1 < 0){ // no person selected yet
                    console.log("1 person selected, requesting info");
                    $scope.cp1 = person.id;
                }
                else {
                    console.log("2nd person selected, requesting info");
                    if ($scope.cp2 < 0) { // second person not yet selected
                        $scope.cp2 = person.id;
                    } else {  // second person already selected, replace selected column
                        if ($scope.selectedCol == 1) {
                            $scope.cp1 = person.id;
                        } else if ($scope.selectedCol == 2) {
                            $scope.cp2 = person.id;
                        } else {
                            $scope.cp1 = $scope.cp2;
                            $scope.cp2 = person.id;
                        }

                    }
                }
                break;

            case "collect":
                apiEngine.personSettask(person.id,"collecting",function(response){
                    person.status.currentCaptchas = 0;
                });
            break;

            case "none":
                console.log("actionNone");
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

    $scope.setClickAction = function(button){
        if(button.clickAction == "none") {
            $scope.cursor = {'cursor': "auto"};
        } else {
            $scope.cursor = {'cursor': "url('resources/images/" + button.image + "'), auto"};
        }
        $scope.clickAction = button.clickAction;
        console.log("ready to " + button.clickAction);
    }

    $scope.buyItem = function(button){
        apiEngine.buy(button.id,function(response){
            apiEngine.getButtons(function(response2){
                $scope.buttons = response2.data.data;
            });
        });
        console.log("Buy " + button.name);
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
        apiEngine.newGame(function(){
            $scope.initializePeople();
            $scope.startingGame=false;
        });
        $scope.updateGamestate();
    };

    $scope.renamePlayer = function(newName) {
        apiEngine.renamePlayer(newName, function(){});
    }

    $scope.selectCol = function(col) {
        $scope.selectedCol = col;
    }

    $scope.getColor = function(param,column){
        if($scope.persons[$scope.cp1] && $scope.persons[$scope.cp2]) {
            var col1 = $scope.persons[$scope.cp1];
            var col2 = $scope.persons[$scope.cp2];
            if (column !== 1) {
                col1 = $scope.persons[$scope.cp2];
                col2 = $scope.persons[$scope.cp1];
            }
            switch (param) {
                case 'age':
                    if (col1.status.age === col2.status.age) return '';
                    else if (col1.status.age <= col2.status.age) return 'green';
                    else return 'red';
                case 'iq':
                    if (col1.abilities.iq === col2.abilities.iq) return '';
                    else if (col1.abilities.iq >= col2.abilities.iq) return 'green';
                    else return 'red';
                case 'speed':
                    if (col1.abilities.speed === col2.abilities.speed) return '';
                    else if (col1.abilities.speed >= col2.abilities.speed) return 'green';
                    else return 'red';
                case 'metabolism':
                    if (col1.abilities.metabolism === col2.abilities.metabolism) return '';
                    else if (col1.abilities.metabolism <= col2.abilities.metabolism) return 'green';
                    else return 'red';
                case 'total':
                    if (col1.getTotalFields() === col2.getTotalFields()) return '';
                    else if (col1.getTotalFields() >= col2.getTotalFields()) return 'green';
                    else return 'red';
            }
        }
    }

    $scope.init();
}

app.directive("actionbutton", function() {
    return {
        scope: {
            name: '=',
            src: '=',
            action: '&'
        },
        template : "<div id=\"action{{name}}\" class=\"actionButton\"></div>",
        replace: true,
        link: function(scope, element, attr) {
            element.css({
                'background-image': 'url(resources/images/' + scope.src +')'
            }),
            element.class;
        }
    };
});

app.directive("shopbutton", function() {
    return {
        scope: {
            name: '=',
            src: '=',
            action: '&'
        },
        template : "<div id=\"shopButton{{name}}\" class=\"shopButton\"></div>",
        replace: true,
        link: function(scope, element, attr) {
            element.css({
                'background-image': 'url(resources/images/' + scope.src +')'
            })
        }
    };
});

app.filter('shopFilter', function() {
    return function(buttons,scope) {
        var output = [];
        angular.forEach(buttons, function(shopButton) {
            var match = false;
            angular.forEach(scope.buttons, function(button){
                if(shopButton.id == button.id) match = true;
            });
            if(!match ) output.push(shopButton);
        });
        return output;
    };
});