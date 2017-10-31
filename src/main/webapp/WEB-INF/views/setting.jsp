<div id="settingScreenArea" class="col-md-3">
	<div class="settingScreen">
		<ul class="nav nav-tabs" id="myTab" role="tablist">
  			<li class="nav-item">
    			<a class="nav-link active" id="game-tab" data-toggle="tab" href="#game" role="tab" aria-controls="game" aria-selected="true">Game</a>
  			</li>
  			<li class="nav-item">
    			<a class="nav-link" id="settings-tab" data-toggle="tab" href="#settings" role="tab" aria-controls="settings" aria-selected="false">Settings</a>
  			</li>
		</ul>
		<div class="tab-content" id="myTabContent">
 			<div class="tab-pane fade show active" id="game" role="tabpanel" aria-labelledby="game-tab">
 				<h1>Game</h1>
 				<div id="playerScore">Score : 1337</div>
 				</br>
 				<div id="actionButtons">
					   <div id="actionEating" class="actionButton" ng-click="setClickAction('eat')"></div>
					   <div id="actionSleeping" class="actionButton" ng-click="setClickAction('sleep')"></div>
					   <div id="actionNone" class="actionButton" ng-click="actionNone()"></div>
					   <div id="actionKill" class="actionButton" ng-click="setClickAction('kill')"></div>
					   <div id="actionUpdate" class="actionButton" ng-click="actionUpdate()"></div>
				</div>
						
				<div id="peopleStat">
				</div>
 			</div>
 			<div class="tab-pane fade" id="settings" role="tabpanel" aria-labelledby="settings-tab">
 				<h1>Settings</h1>
 			</div>
		</div>
	</div>	
</div>
