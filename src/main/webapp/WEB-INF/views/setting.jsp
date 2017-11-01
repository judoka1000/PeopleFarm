<script type="text/javascript" src="resources/controller/dev_controller.js"></script>

<div id="settingScreenArea" class="col-md-3">
	<div class="settingScreen">
		<ul class="nav nav-tabs" id="myTab" role="tablist">
  			<li class="nav-item">
    			<a class="nav-link active" id="game-tab" data-toggle="tab" href="#game" role="tab" aria-controls="game" aria-selected="true">Game</a>
  			</li>
  			<li class="nav-item">
    			<a class="nav-link" id="settings-tab" data-toggle="tab" href="#settings" role="tab" aria-controls="settings" aria-selected="false">Settings</a>
  			</li>
  			<li class="nav-item">
                <a class="nav-link" id="dev-settings-tab" data-toggle="tab" href="#dev-settings" role="tab" aria-controls="dev-settings" aria-selected="false">Dev Settings</a>
            </li>
		</ul>
		<div class="tab-content" id="myTabContent">
 			<div class="tab-pane fade show active" id="game" role="tabpanel" aria-labelledby="game-tab">
 				<h1>Game</h1>
 				<div class="container">
 				    <div class="row">
         				<div id="gameButtons">
         				    <div id="newGameButton" class="" ng-click="newGameAction()">New Game</div>
                		</div>
 				    </div>
 					<div class="row">
		 				<div id="playerScore">Score : {{score}}</div>
		 			</div>
		 			<div class="row">
		 				<div id="actionButtons">
							   <div id="actionEating" class="actionButton" ng-click="setClickAction('eat')"></div>
							   <div id="actionSleeping" class="actionButton" ng-click="setClickAction('sleep')"></div>
							   <div id="actionNone" class="actionButton" ng-click="actionNone()"></div>
							   <div id="actionKill" class="actionButton" ng-click="setClickAction('kill')"></div>
							   <div id="actionTest" class="actionButton" ng-click="setClickAction('test')">test</div>
							   <div id="actionInfo" class="actionButton" ng-click="setClickAction('info')">info</div>
							   <div id="actionCollect" class="actionButton" ng-click="setClickAction('collect')">collect</div>
						</div>
					</div>
				</div>
				</br>
				</br>
				<div id="peopleStat" ng-if="showPeopleId >= 0">
					<h6>People Statistics:</h6>
					<div>ID : {{persons[showPeopleId].id}}</div>
					<div>Age : {{persons[showPeopleId].status.age}}</div>
					<div>Hunger : {{persons[showPeopleId].status.hunger}}</div>
					<div>Tiredness : {{persons[showPeopleId].status.tiredness}}</div>
					<div>IQ : {{persons[showPeopleId].abilities.iq}}</div>
					<div>Speed : {{persons[showPeopleId].abilities.speed}}</div>
					<div>Metabolism : {{persons[showPeopleId].abilities.metabolism}}</div>
				</div>
 			</div>
 			<div class="tab-pane fade" id="settings" role="tabpanel" aria-labelledby="settings-tab">
 				<h1>Settings</h1>
 			</div>
 			<div class="tab-pane fade" id="dev-settings" role="tabpanel" aria-labelledby="dev-settings-tab">
 			    <div ng-app="PeopleApp" ng-controller="DevCtrl" ng-class="cursor">
                    <h1>Dev Settings</h1>
                    <div class="form-check">
                      <label class="form-check-label">
                        <input class="form-check-input" type="checkbox" ng-model="mortalEnabled" ng-change="mortalChanged()">
                        Mortal
                      </label>
                    </div>
                </div>
            </div>
		</div>
	</div>	
</div>
