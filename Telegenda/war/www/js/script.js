$(function() 
{	
	$.ajax(
	{
		type: "GET",
		url: "http://telegenda-webservice.appspot.com/LogoutUrl"
	})
	.done(function( msg ) 
	{		
		$( "body" ).append( "<a href="+msg+">Logout</a>" );
	});
	
	$.ajax(
	{
		type: "GET",
		url: "http://telegenda-webservice.appspot.com/Username"
	})
	.done(function( msg ) 
	{		
		$( "body" ).append( "<p>"+msg+"</p>" );
	});	
});
function submitEvent(calendar, listing)
	{
		$.ajax(
		{
			type: "POST",
			url: "http://telegenda-webservice.appspot.com/Event",
			data:{"calendar":calendar, "listing":listing}
		})
		.done(function( msg ) 
		{		
			alert(msg);
		});
	}
function createOrder(calendarId, keyword)
{
	$.ajax(
	{
		type: "POST",
		url: "http://telegenda-webservice.appspot.com/CalendarCron",
		data:{"calendarid":calendarId, "keyword":keyword}
	})
	.done(function( msg ) 
	{		
		alert(msg);
	});
}
function runNow()
{
	$.ajax(
	{
		type: "GET",
		url: "http://telegenda-webservice.appspot.com/CalendarCron",
	})
	.done(function( msg ) 
	{		
		alert(msg);
	});
}