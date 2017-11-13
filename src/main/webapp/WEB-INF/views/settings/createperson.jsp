<div class="modal fade" id="personModal" tabindex="-1" role="dialog" aria-labelledby="personModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="personModalLabel">Create a Person!</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                 </button>
            </div>
            <div class="modal-body">
                <form name="personForm">
                    <div class="form-check form-check-inline"  ng-init="newPerson.gender='MALE'">
                        <label class="form-check-label">
                            <input class="form-check-input" name="genderRadios" type="radio" id="maleRadio" value="MALE" ng-model="newPerson.gender" ng-checked="true"> Male
                        </label>
                    </div>
                    <div class="form-check form-check-inline">
                        <label class="form-check-label">
                            <input class="form-check-input" name="genderRadios" type="radio" id="femaleRadio" value="FEMALE" ng-model="newPerson.gender"> Female
                        </label>
                    </div>
                    <div class="form-group row">
                        <label for="inputAge" class="col-sm-3 col-form-label">Age</label>
                        <div class="col-sm-9">
                            <input name="age" type="number" class="form-control" id="inputAge" placeholder="Age" ng-model="newPerson.status.age" required>
                            <span ng-show="personForm.age.$touched && personForm.age.$invalid || personForm.age.$error.number">Age is not valid.</span>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="inputIQ" class="col-sm-3 col-form-label">IQ</label>
                        <div class="col-sm-9">
                            <input name="iq" type="number" class="form-control" id="inputIQ" placeholder="IQ" ng-model="newPerson.abilities.iq" required>
                            <span ng-show="personForm.iq.$touched && personForm.iq.$invalid || personForm.iq.$error.number">IQ is not valid.</span>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="inputSpeed" class="col-sm-3 col-form-label">Speed</label>
                        <div class="col-sm-9">
                            <input name="speed" type="number" class="form-control" id="inputSpeed" placeholder="Speed" ng-model="newPerson.abilities.speed" required>
                            <span ng-show="personForm.speed.$touched && personForm.speed.$invalid || personForm.speed.$error.number">Speed is not valid.</span>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="inputMetabolism" class="col-sm-3 col-form-label">Metabolism</label>
                        <div class="col-sm-9">
                            <input name="metabolism" type="number" class="form-control" id="inputMetabolism" placeholder="Metabolism" ng-model="newPerson.abilities.metabolism" required>
                            <span ng-show="personForm.metabolism.$touched && personForm.metabolism.$invalid || personForm.metabolism.$error.number">Metabolism is not valid.</span>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="inputStamina" class="col-sm-3 col-form-label">Stamina</label>
                        <div class="col-sm-9">
                            <input name="stamina" type="number" class="form-control" id="inputStamina" placeholder="Stamina" ng-model="newPerson.abilities.stamina" required>
                            <span ng-show="personForm.stamina.$touched && personForm.stamina.$invalid || personForm.stamina.$error.number">Stamina is not valid.</span>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="inputTiredness" class="col-sm-3 col-form-label">Tiredness</label>
                        <div class="col-sm-9">
                            <input name="tiredness" type="number" class="form-control" id="inputTiredness" placeholder="Tiredness" ng-model="newPerson.status.tiredness" required>
                            <span ng-show="personForm.tiredness.$touched && personForm.tiredness.$invalid || personForm.tiredness.$error.number">Tiredness is not valid.</span>
                         </div>
                    </div>
                    <div class="form-group row">
                        <label for="inputHunger" class="col-sm-3 col-form-label">Hunger</label>
                        <div class="col-sm-9">
                            <input name="hunger" type="number" class="form-control" id="inputHunger" placeholder="Hunger" ng-model="newPerson.status.hunger" required>
                            <span ng-show="personForm.hunger.$touched && personForm.hunger.$invalid || personForm.hunger.$error.number">Hunger is not valid.</span>
                        </div>
                    </div>
                </form>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button id="submitButton" type="button" class="btn btn-primary" ng-click="createPerson()" data-dismiss="modal" ng-disabled="personForm.$invalid">Create</button>
                </div>
             </div>
        </div>
    </div>
</div>