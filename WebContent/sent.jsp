<%@page import="java.sql.*,com.api.model.*,com.api.service.*,java.util.*,java.text.*" %>


<jsp:include page="header.jsp"></jsp:include>
<style>
	.main{
		width: 100vw;
		display: flex;
	}
	.main-col-1{
		display: inline-block;
		width: 40%;
		border:1px solid;
		height: 100vh;
	}
	.main-col-2{
		width: 60%;
		display: inline-block;
		
		border: 1px solid;
		height: 100vh;
		
	}
	
</style>
<div class="main">
			<div class="main-col-1">
				
					<div>
							<h2>Welcome to Mailservices!</h2>		
					</div>
					
							<div>
									<% 	
									if(session.getAttribute("username")!=null){
									String username=(String)session.getAttribute("username");		
									out.print("");
									}
									else{
									request.setAttribute("Error1","Plz Do login First");
									%>
									<jsp:forward page="index.jsp"></jsp:forward>
										<%}
									%>
							</div>
							<div>
									
								<% 
								
							if(session.getAttribute("username")!=null){
							String username=(String)session.getAttribute("username");		
							out.print("<font style='color:navy'>Welcome "+username+"</font><br><h5>Sent Box</h5>");
							if(request.getAttribute("delete")!=null){
							String delete=(String)session.getAttribute("username");		
							out.print("<font style='color:navy'>"+delete+"</font>");
							}
							try {
								
			List<SentBoxModel> mails=SentBoxService.getAllMailsByEmail(username);
			if(mails.size()>0){
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			
			out.print("<div id='bottom'></div>");
		
		out.print("<table border=4 cellspacing='4' cellpadding='5'>");
		out.print("<tr><td>Reciever&nbsp;	&nbsp;</td><td>SUBJECT</td><th>MESSAGE	&nbsp; 	&nbsp;</td><td>DATE OF RECIEVING 	&nbsp;	&nbsp;</td><td>Delete</td></tr>");
		for(SentBoxModel m:mails){
			int id=m.getId();
			
			String date=formatter.format(m.getDate());
			out.print("<tr onclick='viewSentMail("+id+")'>");

			if(m.getReciever().length()>8)
				out.print("<td>" + m.getReciever().substring(0,8) + "....</td>");
			else
				out.print("<td>" +m.getReciever() + "</td>");
			
			if(m.getSubject().length()>5)
				out.print("<td>" + m.getSubject().substring(0,8) + "....</td>");
			else
				out.print("<td>" + m.getSubject() + "</td>");

			if(m.getMessage().length()>5)
				out.print("<td>" + m.getMessage().substring(0,5) + "......</td>");
			else
				out.print("<td>" + m.getMessage()+ "</td>");
			
			out.print("<td>" +date + "</td>");
			
			out.print("<td><a href='deleteSentboxMail.jsp?id="+m.getId() + "'> Delete</a></td>");

			
		
			out.print("</tr>");
		
		}
		out.print("</table>");
		out.print("<table align='right'width='40%'>");
		
		
		out.print("</table>");
			}
			else
			{
				out.print("Sentbox is empty");
			}
	} catch (Exception e) {
		e.printStackTrace();
	}
	}
							
							
							
							else{
							request.setAttribute("Error1","Plz Do login First");
							%>
							<jsp:forward page="index.jsp"></jsp:forward>
								<%
								
								}
							%>
							
							
							</div>
			</div>
			<div  class="main-col-2">
				<table>
					<tr>
						<td style="width: 25%;"></td>
						<td style="width: 65%;">
							<div id="viewSentBoxMail">
			
							</div>
						</td>
						<td style="width: 10%;"></td>
					</tr>
					
				</table>
				
			</div>
		
			
</div>
	
			
	
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>

		function viewSentMail(id)
		{
			$.ajax({url: "GetSentMail.jsp?id="+id, success: function(result){
				
				document.getElementById("viewSentBoxMail").innerHTML=result
    	}
	});
}




</script>				

	