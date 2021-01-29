# Code-Sharing-Platform

Code sharing platform which enables to post your code and add time and number of views restrictions.

### API endpoints
* Get JSON with the code snippet with given id
```sh
GET: /api/code/{id}
```
* Add new code snippet. JSON object should have fields code, time and views. Return id which gives acces to code snippet.
```sh
POST: /api/code/new
```
* Return a JSON array with 10 most recently uploaded code snippets sorted from the newest to the oldest.
```sh
GET: /api/code/latest
```

### Web endpoints
* Return HTML that contains the code snippet with given id 
```sh
/code/id
```
* Return HTML where you can add new code snippet
```sh
/code/new
```
* Return HTML that contains 10 most recently uploaded code snippets
```sh
/code/latest
```

Technologies:
Java,
Spring Boot,
HTML/CSS
