//
//  SummaryViewController.h
//  MyGola
//
//  Created by Ponnie Rohith on 25/07/15.
//  Copyright (c) 2015 PR. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface SummaryViewController : UIViewController
@property (strong, nonatomic) IBOutlet UITextView *summaryTextView;
@property (strong, nonatomic) NSString *location;
-(instancetype)initWithLocation:(NSString*)location;

@end
