##第三方登录
###github认证
[具体步骤见GitHub官网](https://docs.github.com/en/developers/apps/building-oauth-apps/authorizing-oauth-apps)

+   先发送请求给gihub官网（并携带 client_id, redirect_url(将来github回复会发送请求到这个url)  ，state（随机的），scope （获取信息的范围） ）

+   之后待github回复，会收到一个带有code（github随机生成并且有时间限制的序列（一般是10分钟）），之后我们再 发送请求给github，不过这次我们要带有
client_id ,client_secret  ,code 这些参数。


+   最后github会返回一个几个数据，其中便有access_token,之后，我们需要发送带有 ”Authorization： token (acceess_token)“ 的请求头的请求，并等待获取相关用户信息。

###持久化用户信息
+   将获取到的用户信息存入数据库，并添加一个字段为token（随机生成，方便以后做登录状态的维持）

```java
@Controller
public class CallbackController { 
    String callback() {
        
        
        //...........上面的代码省略
        if (githubUser != null) {

            //持久化用户信息
            Long time = System.currentTimeMillis();
            user.setCreate_time(time);
            user.setModify_time(time);
            user.setToken(UUID.randomUUID().toString().substring(0, 36));
            user.setAccount_id(githubUser.getId() + "");
            user.setName(githubUser.getLogin());
            userMapper.insert(user);

        }
    }
}
```

###维持登录状态

+   添加key 为token 的  cookie
    


+   之后每次访问主页的时候都需要获取token 到数据库查询user用户，如果查到的user用户存入session中（在主页有一个基于是否存在user  对象 的 判断，如果true，则直接显示用户名，不是则显示登录连接）。

```java
@Controller
public class IndexController {


    @Resource
    UserMapper userMapper;
    @GetMapping("/")
    public String index(HttpServletRequest request){


        Cookie[] cookies=request.getCookies();
        User user=null;


        for (Cookie cookie :cookies){//利用cookie中的token查找用户信息

            if(cookie.getName().equals("token")){
               user= userMapper.selectByToken(cookie.getValue());
               break;
            }
        }

        //将查找的用户信息放入session中

        request.getSession().setAttribute("user",user);

        return "index";
    }
}
```


```html
 <ul class="nav navbar-nav navbar-right">
                <li class="dropdown" th:if="${session.user != null }">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false" th:text="${session.user.name}"> <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="#">消息中心</a></li>
                        <li><a href="#">个人资料</a></li>
                        <li><a href="#">退出登录</a></li>

                    </ul>
                </li>
                <li th:if="${session.user == null}"><a href="https://github.com/login/oauth/authorize?client_id=47b1457e4ad4dbd281d2&redirect_uri=http://localhost:8080/callback&scope=user&state=241223">登录</a></li>
            </ul>
```