//
//  DidiView.h
//  didiPlugin
//
//  Created by yanjun.li on 16/1/7.
//
//

#import <Cordova/CDVPlugin.h>
#import "DIOpenSDK.framework/Headers/DIOpenSDK.h"


@interface didiPlugin : CDVPlugin
-(void)didiPluginFun:(CDVInvokedUrlCommand *)command;

@end
