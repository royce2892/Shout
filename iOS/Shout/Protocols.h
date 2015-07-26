//
//  Protocols.h
//  MyGola
//
//  Created by Ponnie Rohith on 25/07/15.
//  Copyright (c) 2015 PR. All rights reserved.
//

#ifndef MyGola_Protocols_h
#define MyGola_Protocols_h

typedef NS_ENUM(NSUInteger, Settings) {
    NoMoreShouts,
    NoMoreChatter,
    AllChats,
};

typedef void (^voidBlock)(void);
typedef void (^arrayBlock)(NSArray*);
typedef void (^stringBlock)(NSString*);

#endif
