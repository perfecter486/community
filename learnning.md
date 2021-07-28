##第三方登录
###github认证
[具体步骤见GitHub官网](https://docs.github.com/en/developers/apps/building-oauth-apps/authorizing-oauth-apps)

+先发送请求给gihub官网（并携带 client_id, redirect_url(将来github回复会发送请求到这个url)  ，state（随机的），scope （获取信息的范围） ）

+之后待github回复，会收到一个带有code（github随机生成并且有时间限制的序列（一般是10分钟）），之后我们再 发送请求给github，不过这次我们要带有
client_id ,client_secret  ,code 这些参数。


+最后github会返回一个几个数据，其中便有access_token,之后，我们需要发送带有 ”Authorization： token (acceess_token)“ 的请求头的请求，并等待获取相关用户信息。