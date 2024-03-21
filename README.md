# 通用模块

## 依赖
~~~ kts
// build.gradle.kts
implementation("io.github.xiaobaicz:common:0.0.7")
~~~
~~~ gradle
// build.gradle
implementation 'io.github.xiaobaicz:common:0.0.7'
~~~

## 功能

### 语言

1. LanguageUtils 可直接使用基类 / 基类处理方法 (已集成于Application/FragmentActivity/AppCompatActivity)

~~~ kotlin
// Application & Activity
override fun attachBaseContext(base: Context?) {
    val context = LanguageUtils.handleBaseApplicationContext(base)
    super.attachBaseContext(context)
}

// Activity
override fun onRestart() {
    super.onRestart()
    LanguageUtils.onRestart(this)
}
~~~

~~~ kotlin
// 切换语言
LanguageUtils.setLocale(${Activity}, Locale.CHINA)
// 获取当前语言
LanguageUtils.locale
~~~

### 初始化器
~~~ kotlin
@AutoService(Initializer::class)
class ContextProvider : Initializer {
    override fun onInit(context: Context) {
        // context is application
    }
}
~~~

### Provider
- ObjectProvider
> 实现了获取对象行为
> - 对象为空：等待对象不为空时返回
> - 对象为空：不等待，直接返回null
> - 对象非空：直接返回

- ContextProvider (已集成于ApplicationLifecycleSpi, 使用ObjectProvider实现)
> - applicationContext
> - visibleActivity

### Intent扩展
##### 方法
- parseData                     // 解析传递参数
- startIntent                   // 创建启动Intent
- startActivity                 // 跳转Activity
- finishAndResult               // Activity关闭并返回值
- registerForActivityResult     // 注册返回值接收
- launch                        // 跳转Activity，并等待返回值

### Log
- log 系列方法
- 扩展Log输出 Spi 入口 Log接口