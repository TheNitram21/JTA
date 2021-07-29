# How to get user access tokens
Release 2.0.0 adds the ability to manage user and channel data. But if you only try to run `Channel#setStreamTitle(String)` you will get an Error Response: `401 Unauthorized - Missing User OAUTH token`.

How can you fix this? You need to set an `User Access token`. To get that you need to open a Twitch page while logged in to your twitch account. Copy the following link and replace `<CLIENTID>` with your client id, `<REDIRECTURI>` with your redirect uri and `<SCOPES>` with a space seperated list of your scopes. You can find a list of scopes [here](https://dev.twitch.tv/docs/authentication/#scopes).

`https://id.twitch.tv/oauth2/authorize?client_id=<CLIENTID>&redirect_uri=<REDIRECTURI>&response_type=token&scope=SCOPES`

After opening the page, if done correctly, you should get redirected to a web page with the following pattern:
`<REDIRECTURI>#access_token=<ACCESSTOKEN>`

Now you only need to call `JTABot#setUserAccessToken(String accessToken)` and fill in your access token from the link above. If you now call `Channel#setStreamTitle(String)` you should not get an Error Response. But you can not just call `setStreamTitle(String)` on any random channel, you can only manage users who have authorized your application (opened the web page and authorized your application). You can also only store one user access token.
