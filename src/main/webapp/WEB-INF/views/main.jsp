<!DOCTYPE html>
<html>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.4/angular.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.4/angular-cookies.js"></script>
<script type="text/javascript" src="https://cdn.rawgit.com/JustMaier/6ef7788709d675bd8230/raw/3d39d50e66d8d77e05656ed7dd09298be7e86f1f/ngClickCopy.js"></script>


<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css" integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb" crossorigin="anonymous">
    <link rel="stylesheet" href="resources/css/main.css">

	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js" integrity="sha384-vFJXuSJphROIrBnz7yo7oB41mKfc8JzQZiCq4NCceLEaO4IHwicKwpJf9c9IpFgh" crossorigin="anonymous"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js" integrity="sha384-alpBpkh1PFOepccYVYDB4do5UnbKysX5WZXm3XxPqe5iKTfUKjNkCk9SaVuEZflJ" crossorigin="anonymous"></script>
	
    <script type="text/javascript" src="resources/controller/controller.js"></script>
    <script type="text/javascript" src="resources/api_engine/api_engine.js"></script>
    <script type="text/javascript" src="resources/api_engine/mock.js"></script>

    <script type="text/javascript" src="resources/person/person.js"></script>

</head>
<body ng-app="PeopleApp" ng-controller="PeopleCtrl" ng-class="cursor">

<div class="container-fluid">
	<div class="row">
	    <div class="col-md-9">
	        <div class="row">
	            <div class="col-md-12">
                    <div class="room">
                        <div class="roomRow" ng-repeat="tilerow in tiles">
                            <div class="roomTile" ng-repeat="tile in tilerow">
                            </div>
                        </div>
                        <div ng-repeat="person in persons" class="roomPeople" ng-class="'position-x-' + person.getPosition().x + ' position-y-' + person.getPosition().y + ' ' + person.getCanReproduce(personSelected,clickAction)" ng-show="person.visible" ng-click="personClicked(person)"">
                            <div class="imgContainer" ng-class="'sprite' + person.sprite + ' ' + 'sprite' + person.getAdult()"></div>
                            <div class="newCaptchaCount" ng-class="person.status.captchaChange">{{person.status.currentCaptchas}}</div>
                            <div class="thoughtBubble" ng-if="person.thought !== null">{{ person.thought }}</div>
                            <div class="reproducing" ng-show="person.selectedToReproduce" ng-class="person.heartAnimation"></div>
                        </div>
                    </div>
                </div>
                <div id="PeopleGrid" class="col-md-9">
                    <div id="people_{{person.id}}" class="people" ng-class="'sprite' + person.sprite + ' ' + 'sprite' + person.getAdult() + ' ' + person.fullGender + ' ' + person.getCanReproduce(personSelected,clickAction)" ng-repeat="person in persons" ng-show="person.visible" ng-click="personClicked(person)">
                        <div class="imgContainer">
                            <div class="captchaCount" ng-class="person.status.captchaChange">{{person.status.currentCaptchas}}</div>
                            <div class="reproducing" ng-show="person.selectedToReproduce"></div>
                        </div>
                        <div class="overview">
                            <div class="title"><h1>Worker &num;{{("000" + person.id).slice(-4)}}</h1></div>
                            <p class="percLabel">Tiredness:</p><div class="percBarContainer {{getBarPc(person.status.tiredness)}}"><div ng-style="{'width': person.status.tiredness + '%' }">{{person.status.tiredness}}</div></div>
                            <p class="percLabel">Hunger:</p><div class="percBarContainer {{getBarPc(person.status.hunger)}}"><div ng-style="{'width': person.status.hunger + '%' }">{{person.status.hunger}}</div></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
		<jsp:include page="setting.jsp" />	
	</div>
</div>
<footer>
	<small class="rainbow">&copy; Copyright 2017, PeopleFarm Company</small>
</footer>
</body>
</html>
