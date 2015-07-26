//
//  SummaryViewController.m
//  MyGola
//
//  Created by Ponnie Rohith on 25/07/15.
//  Copyright (c) 2015 PR. All rights reserved.
//

#import "SummaryViewController.h"
#import "ParseDataHandler.h"
@interface SummaryViewController ()

@end

@implementation SummaryViewController

- (instancetype)initWithLocation:(NSString *)location
{
    self = [super init];
    if (self) {
        self.location = location;
        self.summaryTextView.text = @" ";
    }
    return self;
}
-(void)viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:NO];
    [ParseDataHandler getSummaryForLocation:self.location andCompBlock:[self getSummaryCompletion]];
}
-(stringBlock)getSummaryCompletion
{
    return ^(NSString* string){
        self.summaryTextView.text = string;
    };
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


@end
