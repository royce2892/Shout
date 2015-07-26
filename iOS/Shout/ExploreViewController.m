//
//  ExploreViewController.m
//  MyGola
//
//  Created by Ponnie Rohith on 25/07/15.
//  Copyright (c) 2015 PR. All rights reserved.
//

#import "ExploreViewController.h"
#import "SummaryViewController.h"
#import "SafetyTipsViewController.h"
#import "LocalPhrasesViewController.h"

NSString *const kSummary = @"Summary";
NSString *const kSafetyTips = @"SafetyTips";
NSString *const kLocalPhrases = @"LocalPhrases";

@interface ExploreViewController ()

@end

@implementation ExploreViewController

-(instancetype)initWithLocation:(NSString*)location
{
    self = [super init];
    if (self) {
        self.location = location;
    }
    return self;
}

-(void)viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:animated];
    NSMutableArray *localViewControllersArray = [[NSMutableArray alloc] initWithCapacity:3];
    
    SummaryViewController *summaryViewController = [[SummaryViewController alloc]initWithLocation:self.location];
    [localViewControllersArray addObject:summaryViewController];
    summaryViewController.title = kSummary;
    summaryViewController.tabBarItem.title = kSummary;
    summaryViewController.tabBarItem.image = [UIImage imageNamed:@"home"];

    SafetyTipsViewController *safetyTipsViewController = [[SafetyTipsViewController alloc]initWithLocation:self.location];
    [localViewControllersArray addObject:safetyTipsViewController];
    safetyTipsViewController.title = kSafetyTips;
    safetyTipsViewController.tabBarItem.title = kSafetyTips;
    safetyTipsViewController.tabBarItem.image = [UIImage imageNamed:@"safety"];
    
    LocalPhrasesViewController *localPhrasesViewController = [[LocalPhrasesViewController alloc]initWithLocation:self.location];
    [localViewControllersArray addObject:localPhrasesViewController];
    localPhrasesViewController.title = kLocalPhrases;
    localPhrasesViewController.tabBarItem.title = kLocalPhrases;
    localPhrasesViewController.tabBarItem.image = [UIImage imageNamed:@"translate"];;
    
    self.viewControllers = localViewControllersArray;
    self.view.autoresizingMask=(UIViewAutoresizingFlexibleHeight);

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
