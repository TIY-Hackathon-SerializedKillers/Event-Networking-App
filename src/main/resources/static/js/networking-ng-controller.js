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
            console.log("Container we're about to send: " + newUser.email + " " + newUser.firstName + " " + newUser.lastName + " " + newUser.password + " " + newUser.techSkills);

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

        $scope.createNewEvent = function(newEventName, newEventLocation, newEventDate, newEventTime) {
             console.log("In createNewEvent function in ng controller");

             //Make a container
             var newEvent = {
                 name: newEventName,
                 location: newEventLocation,
                 date: newEventDate,
                 time: newEventTime
             }

             $http.post("/addEvent.json", newEvent)
                 .then(
                     function successCallback(response) {
                         console.log(response.data);
                         console.log("Adding data to scope");
                         $scope.allEvents = response.data;
                     },
                     function errorCallback(response) {
                         console.log("Unable to get data...");
                     });
         };

        $scope.joinEvent = function(myUserId, eventIWantToJoinId) {
             console.log("In joinEvent function in ng controller");

             //Make a container
             var newUserEvent = {
                  userId: myUserId,
                  eventId: eventIWantToJoinId
             }

             $http.post("/joinEvent.json", newUserEvent)
                  .then(
                     function successCallback(response) {
                         console.log(response.data);
                         console.log("Adding data to scope");
                         // Returns a list of attendees
                         $scope.eventAttendees = response.data;
                     },
                     function errorCallback(response) {
                         console.log("Unable to get data...");
                     });
        };

//        $scope.viewUserInfo = function(requesterUserId, requesteeUserId) {
//             console.log("In viewUserInfo function in ng controller");
//
//             //Make a container
//             var idContainer = {
//                  userId: requesterUserId,
//                  friendId: requesteeUserId
//             }
//
//             $http.post("/viewUserInfo.json", idContainer)
//                  .then(
//                     function successCallback(response) {
//                         console.log(response.data);
//                         console.log("Adding data to scope");
//                         // Returns container with error or user
//                         $scope.friendContainer = response.data;
//                     },
//                     function errorCallback(response) {
//                         console.log("Unable to get data...");
//                     });
//        };

        $scope.addToMyFriendList = function (myUserIdAllow, friendUserIdAllow) {
             console.log("In addToMyFriendList function in ng controller");

             //Make a container
             var idFriendContainer = {
                  userId: myUserIdAllow,
                  userWhoWantsToBeFriendId: friendUserIdAllow
             }

             $http.post("/addToMyFriendList.json", idFriendContainer)
                  .then(
                     function successCallback(response) {
                         console.log(response.data);
                         console.log("Adding data to scope");
                         // Returns container with error or user
                         $scope.addToMyFriendListUser = response.data;
                     },
                     function errorCallback(response) {
                         console.log("Unable to get data...");
                     });
        };

        $scope.checkIfIHaveAccess = function(myUserIdCheck, theirUserIdCheck) {
             console.log("In checkIfIHaveAccess function in ng controller");

             //Make a container
             var doIHaveAccessContainer = {
                  userId: theirUserIdCheck,
                  userWhoWantsToBeFriendId: myUserIdCheck
             }

             $http.post("/viewUserInfo.json", doIHaveAccessContainer)
                  .then(
                     function successCallback(response) {
                         console.log(response.data);
                         console.log("Adding data to scope");
                         // Returns container with error or user
                         $scope.doIHaveAccessResponse = response.data;
                     },
                     function errorCallback(response) {
                         console.log("Unable to get data...");
                     });
        };


        console.log("Page loaded!");

    });