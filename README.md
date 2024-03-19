# 通用模块

## 依赖
~~~ kts
// build.gradle.kts
implementation("io.github.xiaobaicz:common:0.0.6")
~~~
~~~ gradle
// build.gradle
implementation 'io.github.xiaobaicz:common:0.0.6'
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

1. Initializer

~~~ kotlin
@AutoService(Initializer::class)
class ContextProvider : Initializer {
    override fun onInit(context: Context) {
        // context is application
    }
}
~~~

### RecyclerView扩展
#### Adapter抽象
- BindingAdapter (实现ViewBinding抽象)
- BindingListAdapter (在ViewBinding抽象基础上实现DiffUtil，刷新数据)
#### Adapter简易实现
- SimpleAdapter
~~~ kotlin
val rv: RecyclerView
val adapter = rv.simpleAdapter<ViewBinding, Bean> { v, i, p ->
    // v: ViewBinding
    // i: Bean
    // p: Position
}
// 重新赋值刷新数据，DiffUtil自动刷新
adapter.data = listOf(x, x, x)
~~~

- CombineAdapter
~~~ kotlin
val rv: RecyclerView
val adapter = rv.combineAdapter {
    bind<ViewBinding1, Bean1>(viewType1) { v, i, p ->
        // v: ViewBinding1
        // i: Bean1
        // p: Position
    }
    bind<ViewBinding2, Bean2>(viewType2) { v, i, p ->
        // v: ViewBinding2
        // i: Bean2
        // p: Position
    }
}
// 重新赋值刷新数据，DiffUtil自动刷新
adapter.data = listOf(x, x, x)
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