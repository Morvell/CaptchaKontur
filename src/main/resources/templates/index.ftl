<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>

    <form method="post">
        <table cellspacing="15">

            <tr>
                <td>Are you human?</td>
                <td><input type="text" name="code"></td>
            </tr>

        </table>

        <br>
        <img src="data:image/png;base64,${captcha}"/>

        <br><br>
        <input type="submit" value="submit">


    </form>
    <br><br>
</body>
</html>