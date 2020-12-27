#  Banana Photo Store



## Available end-points

###/tags
/tags  Returns all tags

>[{"id":1,"tag":"Banana"}
> ,{"id":2,"tag":"Fruit"}]\


/tags/{id} Query a tag by Id, returns array of photos with user that uploaded that photo:
>{"id":1,"tag":"Banana","photos":[{"id":1,"fileName":"banana.jpg","width":800,"height":600,"price":5.99,"date":"2020-12-27","title":"Lonely Banana","downloads":4,"user":{"id":1,"userName":"msiqueira","email":"bps@gmail.com","password":"","signUpDate":"2020-12-27","soldList":[],"boughtList":[]}}]}

/tags/tag={string} Query a tag by its name, returns array of photos. example: /tags=banana
>{"id":1,"tag":"Banana","photos":[{"id":1,"fileName":"banana.jpg","width":800,"height":600,"price":5.99,"date":"2020-12-27","title":"Lonely Banana","downloads":4,"user":{"id":1,"userName":"msiqueira","email":"bps@gmail.com","password":"","signUpDate":"2020-12-27","soldList":[],"boughtList":[]}}]}


###/photos
/photos  Returns all photos
> [{"id":1,"fileName":"banana.jpg","width":800,"height":600,"price":5.99,"date":"2020-12-27","title":"Lonely Banana","downloads":4,"user":{"id":1,"userName":"msiqueira","email":"bps@gmail.com","password":"","signUpDate":"2020-12-27","soldList":[],"boughtList":[]},"tags":[{"id":1,"tag":"Banana"},{"id":2,"tag":"Fruit"}]}]

/photos/{id} Query a photo by Id, returns array of tags, and the user that uploaded that photo:
> {"id":1,"fileName":"banana.jpg","width":800,"height":600,"price":5.99,"date":"2020-12-27","title":"Lonely Banana","downloads":4,"user":{"id":1,"userName":"msiqueira","email":"bps@gmail.com","password":"","signUpDate":"2020-12-27","soldList":[],"boughtList":[]},"tags":[{"id":2,"tag":"Fruit"},{"id":1,"tag":"Banana"}]}

###/transactions
/transactions Returns all transactions
> [{"id":1,"date":"2020-12-27","listPrice":5.99}]

/transactions/{id} Query a transaction by Id.
> [{"id":1,"date":"2020-12-27","listPrice":5.99}]

###/users
/users Returns all users
> [{"id":1,"userName":"msiqueira","email":"mcksiq@gmail.com","password":"mcksiq@gmail.com","signUpDate":"2020-12-27","soldList":[],"boughtList":[]},{"id":2,"userName":"someone","email":"s@gmail.com","password":"s@gmail.com","signUpDate":"2020-12-27","soldList":[],"boughtList":[]}]

/users/{id} Query by Id. Returns users with all uploaded photos, bought transactions, and sold transactions:
> {"id":1,"userName":"msiqueira","email":"mcksiq@gmail.com","password":"","signUpDate":"2020-12-27","boughtTransactions":[],"soldTransactions":[{"id":1,"date":"2020-12-27","listPrice":5.99,"buyer":null}],"photos":[{"id":1,"fileName":"banana.jpg","width":800,"height":600,"price":5.99,"date":"2020-12-27","title":"Lonely Banana","downloads":4,"tags":[],"user":null}]}
