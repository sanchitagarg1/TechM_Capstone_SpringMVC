<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>OTP Verification</title>
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

.container form {
    width: 50%;
    padding: 2rem;
    box-shadow: 0 1rem 2rem rgba(0, 0, 0,.3);
    border-radius: .5rem;
    background: #333;
}

.container form .inputbox {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
}

.container form .inputbox input {
    width: 100%;
    margin: 1rem 0;
    padding: 1rem;
    font-size: 1.7rem;
    color: #333;
    background: #f7f7f7;
    text-transform: none;
    border-radius: 1rem;
}

.container form .btn {
    display: inline-block;
    margin-top: 1rem;
    background: var(--orange);
    color: #fff;
    padding: .8rem 3rem;
    border: .2rem solid var(--orange);
    cursor: pointer;
    font-size: 1.7rem;
}

.container form .btn:hover {
    background: rgba(255,165,0,.2);
    color: var(--orange);
}

@media (max-width:768px) {
    .container form {
        width: 70%;
    }
}

@media (max-width:500px) {
    .container form {
        width: 90%;
    }
}

@media (max-width:300px) {
    .container form {
        width: 100%;
    }
}
</style>
</head>
<body>
    <div class="container">
        <form action="MainController" method="POST">
            <div class="inputbox">
                <input type="text" name="otp" placeholder="Enter OTP" required>
                <input type="hidden" name="myotp" value="<%=request.getAttribute("otp") %>">
                <input type="password" name="upass" placeholder="New Password" required>
                <input type="password" name="upassConfirm" placeholder="Re-enter Password" required>
                <input type="hidden" name="email" value="<%=request.getAttribute("email") %>">
            </div>
            <input type="submit" value="Verify OTP and Change Password" class="btn">
        </form>
    </div>
</body>
</html>
