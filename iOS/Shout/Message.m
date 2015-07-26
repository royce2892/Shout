//
//  Message.m
//  Kaizen
//
//  Created by Ponnie Rohith on 28/03/15.
//  Copyright (c) 2015 PR. All rights reserved.
//

#import "Message.h"
#import "AppDelegate.h"

@implementation Message


- (instancetype)initWithText:(NSString*)messageText isSender:(BOOL)isSender
{
    self = [super init];
    if (self) {
        self.text = messageText;
//        self.time = [NSDate date];
        self.isSender = isSender;
    }
    return self;
}

@end
