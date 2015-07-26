//
//  PopoverViewController.h
//  OlaAppathon
//
//  Created by Ponnie Rohith on 08/02/15.
//  Copyright (c) 2015 PR. All rights reserved.
//


#import <UIKit/UIKit.h>

@protocol PopOverTableDelegate <NSObject>
@optional
- (void)didSelectRowAtIndex:(NSUInteger)index;

@end


@interface PopoverViewController : UITableViewController
@property (nonatomic,weak)id<PopOverTableDelegate> delegate;

@end