//
//  ParseDataHandler.m
//  TrackMyTrain
//
//  Created by Ponnie Rohith on 24/05/15.
//  Copyright (c) 2015 PR. All rights reserved.
//

#import "ParseDataHandler.h"
#import <Bolts/Bolts.h>
#import "Message.h"
NSString* const kName = @"Name";
NSString* const klocation = @"location";
NSString* const kMessage = @"Message";

@implementation ParseDataHandler

+(void)getSummaryForLocation:(NSString*)location andCompBlock:(stringBlock)compBlock
{
    NSPredicate *predicate = [NSPredicate predicateWithFormat:@"Name == %@",location];
    PFQuery *query = [PFQuery queryWithClassName:klocation predicate:predicate];
    [query findObjectsInBackgroundWithBlock:^(NSArray *objects, NSError *error) {
        if (!error) {
                PFObject *object = objects[0];
                NSString *summary = object[@"wiki"];
                [summary stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceAndNewlineCharacterSet]];
                compBlock(summary);
        } else {
            // Log details of the failure
            NSLog(@"Error: %@ %@", error, [error userInfo]);
        }
    }];
}
+(void)getSafetyTipsForLocation:(NSString*)location andCompBlock:(arrayBlock)compBlock
{
    NSMutableArray *safetyTips = [NSMutableArray new];
    NSPredicate *predicate = [NSPredicate predicateWithFormat:@"Name == %@",location];
    PFQuery *query = [PFQuery queryWithClassName:klocation predicate:predicate];
    [query findObjectsInBackgroundWithBlock:^(NSArray *objects, NSError *error) {
        if (!error) {
            for (int i = 0 ;i < objects.count ; i++) {
                PFObject *object = objects[i];
                NSString *tipString = object[@"safety"];
                NSArray *components = [tipString componentsSeparatedByCharactersInSet:[NSCharacterSet newlineCharacterSet]];
                [safetyTips addObjectsFromArray:components];
                compBlock(safetyTips);
            }
        } else {
            // Log details of the failure
            NSLog(@"Error: %@ %@", error, [error userInfo]);
        }
    }];
}
+(void)getLocalPhrasesForLocation:(NSString*)location andCompBlock:(arrayBlock)compBlock
{
    NSMutableArray *localPhrases = [NSMutableArray new];
    NSPredicate *predicate = [NSPredicate predicateWithFormat:@"Name == %@",location];
    PFQuery *query = [PFQuery queryWithClassName:klocation predicate:predicate];
    [query findObjectsInBackgroundWithBlock:^(NSArray *objects, NSError *error) {
        if (!error) {
            for (int i = 0 ;i < objects.count ; i++) {
                PFObject *object = objects[i];
                NSString *tipString = object[@"local"];
                NSArray *components = [tipString componentsSeparatedByString:@"|"];
                [localPhrases addObjectsFromArray:components];
                compBlock(localPhrases);
            }
        } else {
            // Log details of the failure
            NSLog(@"Error: %@ %@", error, [error userInfo]);
        }
    }];
}

+(void)getAllMessagesWithCompBlock:(arrayBlock)compBlock
{
    NSMutableArray *messages = [NSMutableArray new];
    PFQuery *query = [PFQuery queryWithClassName:kMessage];
    [query findObjectsInBackgroundWithBlock:^(NSArray *objects, NSError *error) {
        if (!error) {
            for (int i = 0 ;i < objects.count ; i++) {
                Message *message;
                PFObject *object = objects[i];
                NSString *content = object[@"content"];
                NSString *from = object[@"from"];
                if ([[AppDelegate retrieveUserName] isEqualToString:from]) {
                    message = [[Message alloc]initWithText:content isSender:YES];
                }
                else{
                    message = [[Message alloc]initWithText:content isSender:NO];
                }
                [messages addObject:message];
                compBlock(messages);
            }
        } else {
            // Log details of the failure
            NSLog(@"Error: %@ %@", error, [error userInfo]);
        }
    }];
}
+(void)saveMessageInParse:(Message*)message
{
    PFObject *messageObject = [PFObject objectWithClassName:kMessage];
    messageObject[@"content"] = message.text;
    messageObject[@"from"] = [AppDelegate retrieveUserName];
    [messageObject saveInBackgroundWithBlock:^(BOOL succeeded, NSError *error) {
        if (succeeded) {
            NSLog(@"The object has been saved.");
        } else {
            NSLog(@"There was a problem, check error.description");
        }
    }];
    [messageObject pinInBackground];

}
+(void)pushMessage:(NSString*)message
{
    PFPush *push = [[PFPush alloc] init];
    [push setChannel:@"shout"];
    [push setData:@{ @"content" : message , @"from" : [AppDelegate retrieveUserName]}];
    [push sendPushInBackgroundWithBlock:^(BOOL succeeded, NSError *error) {
        if (succeeded) {
            NSLog(@"push sucess");
        }
        else{
            NSLog(@"push failed");
        }
    }];
  
}
@end
