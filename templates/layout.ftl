<#macro page_body>  
  <p>This is a placeholder</p>
</#macro>

<#macro display_page>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

	<link rel="icon" type="image/png" sizes="32x32" href="/static/favicon-32x32.png">
	<link rel="icon" type="image/png" sizes="96x96" href="/static/favicon-96x96.png">
	<link rel="icon" type="image/png" sizes="16x16" href="/static/favicon-16x16.png">	
	
    <title>${title}</title>

    <meta name="description" content="Main template page, based on Bootstrap">
    <meta name="author" content="Vincenzo La Spesa https://github.com/VincenzoLaSpesa">

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

  </head>
  <body>
	<@page_body/>
  <hr>
  </body>
</html>

</#macro>