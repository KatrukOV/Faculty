<html>
<head>
    <title>Registration</title>
</head>

<body style="text-align:center;">

<h3>Create your login</h3>
<hr/>
<form method="POST">
    <label>Last name: </label>
    <input type="text" name="lastName" placeholder="Last Name"/>
    <br>
    <label>Name: </label>
    <input type="text" name="name" placeholder="Name"/>
    <br>
    <label>Patronymic: </label>
    <input type="text" name="patronymic" placeholder="Patronymic"/>
    <br>
    <label>Username: </label>
    <input type="text" name="username" placeholder="Username"/>
    <br>
    <label>Password: </label>
    <input type="password" name="password" placeholder="Password"/>
    <br>
    <label>Password repeat: </label>
    <input type="password" name="confirmPassword" placeholder="Confirm Password"/>
    <br>
    <input type="hidden" name="command" value="registration"/>
    <input type="submit" value="Sign up"/>
</form>
<hr/>
<a href="/">
    <input type="button" value="Back to login"/>
</a>
</body>
</html>
