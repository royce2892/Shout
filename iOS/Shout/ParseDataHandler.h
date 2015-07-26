//
//  ParseDataHandler.h
//  TrackMyTrain
//
//  Created by Ponnie Rohith on 24/05/15.
//  Copyright (c) 2015 PR. All rights reserved.
//

@class Message;
@interface ParseDataHandler : NSObject

+(void)getSummaryForLocation:(NSString*)location andCompBlock:(stringBlock)compBlock;
+(void)getSafetyTipsForLocation:(NSString*)location andCompBlock:(arrayBlock)compBlock;
+(void)getLocalPhrasesForLocation:(NSString*)location andCompBlock:(arrayBlock)compBlock;
+(void)getAllMessagesWithCompBlock:(arrayBlock)compBlock;
+(void)saveMessageInParse:(Message*)message;
+(void)pushMessage:(NSString*)message;

@end
