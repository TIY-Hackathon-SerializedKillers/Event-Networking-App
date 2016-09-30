angular.module('NetworkingAngularApp', [])
   .controller('SampleController', function($scope, $http, $timeout) {
        $scope.register = function(email, firstName, lastName, password, techSkills) {
            console.log("In register function in ng controller");

            //Make a container
            var newUser = {
                email: email,
                firstName: firstName,
                lastName: lastName,
                password: password,
                techSkills: techSkills
            }

            $http.post("/register.json", newUser)
                .then(
                    function successCallback(response) {
                        console.log(response.data);
                        console.log("Adding data to scope");
                        $scope.loginContainer = response.data;
                    },
                    function errorCallback(response) {
                        console.log("Unable to get data...");
                    });
        };

        $scope.login = function(loginEmail, loginPassword) {
            console.log("In login function in ng controller");

            //Make a container
            var returningUser = {
                email: loginEmail,
                password: loginPassword
            }

            $http.post("/login.json", returningUser)
                .then(
                    function successCallback(response) {
                        console.log(response.data);
                        console.log("Adding data to scope");
                        $scope.loginContainerForLogin = response.data;
                    },
                    function errorCallback(response) {
                        console.log("Unable to get data...");
                    });
        };




    });