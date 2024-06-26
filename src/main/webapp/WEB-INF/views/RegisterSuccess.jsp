<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Success</title>
<style>
:root {
    --orange: #ffa500;
}

* {
    font-family: 'Nunito', sans-serif;
    margin: 0;
    padding: 0;
    box-sizing: border-box;
    text-transform: capitalize;
    outline: none;
    border: none;
    text-decoration: none;
    transition: all .2s linear;
}

html {
    font-size: 62.5%;
    overflow-x: hidden;
    scroll-padding-top: 6rem;
    scroll-behavior: smooth;
}

.container {
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
    height: 100vh;
    width: 100%;
    background-color: #333;
}

.container h1 {
    font-size: 3rem;
    text-align: center;
    color: var(--orange);
}

.container p {
    font-size: 2rem;
    text-align: center;
}

.container p > a {
    text-decoration: none;
    color: var(--orange);
}

.container p > a:hover {
    text-decoration: underline;
}

</style>
</head>
<body>
    <div class="container">
        <h1>Operation Successful!</h1>
        <p><a href="Main.jsp">Go to Home Page</a></p>
    </div>
</body>
</html>
