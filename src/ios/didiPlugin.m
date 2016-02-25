//
//  DidiView.h
//  didiPlugin
//
//  Created by yanjun.li on 16/1/7
//
//

#import "didiPlugin.h"
#import "AppDelegate.h"

@implementation didiPlugin

//NSString *appcode = @"801101";
//- (CDVPlugin *)initWithWebView:(UIWebView *)theWebView
//{
//    SDK_ENVIRONMENT = @"UAT";
//    return [super initWithWebView:theWebView];
//}


- (void)didiPluginFun:(CDVInvokedUrlCommand *)command;
{
    // NSLog(@"test begin1231");
    // UIAlertView *alertview = [[UIAlertView alloc] initWithTitle:@"标题" message:@"你好DIDI！" delegate:self cancelButtonTitle:@"取消" otherButtonTitles:@"确定", nil];
    // [alertview show];
//    [self setViewController:[super viewController]];
//    
//    UIViewController *uc=[[UIViewController alloc] init];
//        NSLog(@"ready to open didi");
    NSString * key =@"didi63482F624F483537684769725451";
    NSString * secret = @"7edb7e650d42d62b58b4de287cb4081e";
    
    [DIOpenSDK registerApp:key secret: secret];
    DIOpenSDKRegisterOptions *op=[[DIOpenSDKRegisterOptions alloc] init];
    //      op.biz=@"5";
    //    op.fromname=@"宜达新居";
    //    "31.1738240000,121.1631880000"
    //  op.fromlat=@"31.1738240000";
    //    op.fromlng=@"121.1631880000";
    //    op.maptype=@"baidu";
    //    [op setf]
    //    op.fromlat=@"青浦区华盈路1085号";
    //    op.phone=@"18510971107";
    
    [DIOpenSDK showDDPage:[self viewController]
                 animated:YES
                   params:op
                 delegate:nil];
    NSLog(@"end open");

    
}




@end
