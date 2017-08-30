# SignatureView
### 简介

SignatureView是一个简单的手写签名View。

### 使用

 首先在布局文件中引入view
```xml  
 <cn.xpcheng.signatureview.view.SignatureView
        android:id="@+id/sign_view"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1"/>
 ```
 然后在Activity/Fragment中初始化（findViewById）：
 ``` java 
   SignatureView mSignatureView = (SignatureView) findViewById(R.id.sign_view);
   ```
 
### 方法介绍
 1. 设置画笔宽度：默认画笔宽度为5,可使用`mSignatureView.setLineWidth(10);`来修改画笔宽度。
 
2. 设置画笔颜色：默认画笔颜色为黑色,可使用`mSignatureView.setLineColor(Color.GREEN);`来修改画笔宽度。
 
3. clear：  调用`mSignatureView.clear()`能清除界面上的签名。

4. getBitmap：`mSignatureView.getBitmap();`讲界面转化为bitmap。


5. 保存签名：`mSignatureView.saveBitmap(path);`方法保存签名照片，传入需要保存的路径，保存格式为jpeg。
 
 
**需要注意的是使用保存照片方法需要在`AndroidManifest`里面添加写入文件权限。**
