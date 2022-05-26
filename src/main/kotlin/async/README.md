异步编程常见解决方案及对回掉地狱的处理

解决案例:

```txt
有三个URL:"url1","url2","url3"
模拟后一个依赖前一个返回值的情况 2级回调
并在主线程中获取最后一个返回值的情况
分别使用以下不同模式完成并比较优缺点
1.callback
2.future
3.promise
4.async/await
5.coroutine
体会解决方案优化发展的过程
```