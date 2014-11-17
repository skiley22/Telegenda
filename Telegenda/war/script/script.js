$(function()
{
	var clientId = '769449426385-h1qni0it927etkeb3kpd4h1tru5g78q1.apps.googleusercontent.com';
	var apiKey = 'AIzaSyAxpZVJehgvcmPeI9XZEhCcTmAuT3sbxKk';
	var scopes = 'https://www.googleapis.com/auth/calendar';

	var config = 
	{
          'client_id': clientId,
          'scope': scopes
    };
    gapi.auth.authorize(config, function() {
    //console.log('login complete');
	//	console.log(gapi.auth.getToken());
	alert(gapi.auth.getToken());
	});
	

	/*$.ajax(
		{
			type: "POST",
			url: "https://enernet-calc-engine-001.appspot.com/api/v1/measure_calc",
			data: 
			{ 
				id: "400ced5c-65b6-4711-8442-f4160121",
				projectedsigndate: "2014-05-07-11-43",
				program: "HES",
				ecmfan: "Yes",
				fueltype: "Gas",
				existingafueknown: "false",
				existingafue: "0",
				homesqft: "2300",
				yearhomebuilt: "1987",
				yearfurnaceinstalled: "1988",
				newafue: "95"
			}
		})
		.done(function( msg ) 
		{
			alert( "Data Saved: " + msg );
		});
	});*/
});