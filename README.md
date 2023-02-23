# TitleLayoutDemo

## 需求：

之前看浏览器有很方便的布局结构，文字标题栏，和它所对应的布局，可以通过点击标题栏或者滑动视图来进行切换。有点好奇，想自己弄一个，虽然有官方API提供的FragmentPagerAdapter+ViewPager+TabLayout可以实现相同的效果。如果我想自定里面的下划线长度，颜色，边距等的具体参数会不是那么方便。为了自己的方便，我决定模拟一下这个功能。

效果如下：



<img src="E:\TitleLayoutDemo\8.jpg" style="zoom:50%;" />

结构分析：主要分上下2层：标题区和内容区。



![](E:\TitleLayoutDemo\9.png)

标题区：字符串命名，带下划线，被选中的项会染上蓝色，点击标题区可以切换内容区。包含以下：

子容器：FlagTextView

FlagTextView绘制了字体和下划线的颜色和大小，存储了对应的内容区对象。

父容器：TapsMenu

TapsMenu负责排列FlagTextView和进行点击时的切换操作。





内容区（ContentLayout）：里面显示独立的内容，和标题区关联，水平滑动内容区可以切换标题区的选择。

内容图（ContentView）：布局类，存储对应的FlagTextView。

