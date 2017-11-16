function deleteKeyword(keyword)
{
	$.ajax(
	{
		type: "DELETE",
		url: "http://telegenda-185619.appspot.com/SavedKeywords?id=" + keyword.keywordId,
		async:false
	})
	.done(function( msg )
	{
		alert(msg);
	});
}