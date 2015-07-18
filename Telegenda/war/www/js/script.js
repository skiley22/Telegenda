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