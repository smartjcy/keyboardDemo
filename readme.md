

// demo基于 SValence老师的  https://github.com/SValence/SafeKeyboard.git 库 做了个性化处理
// 请大家支持原作者，觉得此demo有点可取之处的，随意点个star [doge]

// 添加依赖和使用 1->2->3

//1. project/build.gradle

 allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
 }

//2. app/build.gradle

dependencies {
	implementation 'com.github.smartjcy:keyboardDemo:v1.0.0'
 }

//3. 使用参考app/src/main/java/com.smartjcy.demo.keyboard.MainActivity