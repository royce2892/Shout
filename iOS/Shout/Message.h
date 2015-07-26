//
//  Message.h
//  Kaizen
//
//  Created by Ponnie Rohith on 28/03/15.
//  Copyright (c) 2015 PR. All rights reserved.
//

@interface Message : NSObject
@property (nonatomic, strong) NSString *text;
//@property (nonatomic, strong) NSDate *time;
@property  BOOL isSender;
- (instancetype)initWithText:(NSString*)messageText isSender:(BOOL)isSender;

@end
