
<%  /*/****************************************************************************
CSE532 -- Project 2
File name: insert.jsp
Author(s): Harshini Kanaparthi (112687951 )
Meghana Vemulapalli (112670836 )
Brief description: This file is a jsp that displays the tables from the queries retrieved by java servlet page 
****************************************************************************/ */ >
%>

<!DOCTYPE html> 
<html> 
<head> 
<style>




form {
  background-color: white;
  width: 450px;
  border: 10px solid green;
  padding: 150px;
  margin: 10px;
}

h{
  font-weight: bold;
  color: #4CAF50;
  font-size: 32px;
}

input[type=submit] {
  background-color: #4CAF50; /* Green */
  border: none;
  color: white;
  padding: 25px 142px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin: 10px 2px;
  -webkit-transition-duration: 0.4s; /* Safari */
  transition-duration: 0.4s;
  cursor: pointer;
}

div {
  border-radius: 5px;
  background-color: #f2f2f2;
  padding: 20px;
  text-align: center;
}

input[type=submit]:hover {
 background-color: white; 
  color: black; 
  border: 2px solid #4CAF50;
  
}
</style>

</head> 
<body> 

<div>

    <form name="form1" action="./InsertData" method="post">  
		<div padding: 80px><h text-align: left padding: 80px font-size: 40px>WOCO DataBase Query</h></div><br/><br/><br/>
        <input type="submit" value="query1" name="q1"/> <br/><br/><br/>
        <input type="submit" value="query2" name="q1"/> <br/><br/><br/>
        <input type="submit" value="query3" name="q1"/> <br/><br/><br/>
        <input type="submit" value="query4" name="q1"/> <br/><br/><br/>
        <input type="submit" value="query5" name="q1"/> <br/><br/><br/>
    </form> 
</div>    
</body> 
</html> 