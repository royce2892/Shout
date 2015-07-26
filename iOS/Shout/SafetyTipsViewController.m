//
//  SafetyTipsViewController.m
//  TrackMyTrain
//
//  Created by Ponnie Rohith on 23/05/15.
//  Copyright (c) 2015 PR. All rights reserved.
//

#import "SafetyTipsViewController.h"
#import "ParseDataHandler.h"

@interface SafetyTipsViewController ()

@end

NSArray *safetyTips;
@implementation SafetyTipsViewController

-(instancetype)initWithLocation:(NSString*)location
{
    self = [super init];
    if (self) {
        self.location = location;
    }
    return self;
}
- (void)viewDidAppear:(BOOL)animated {
    [super viewDidAppear:animated];
    // Do any additional setup after loading the view from its nib.
    [ParseDataHandler getSafetyTipsForLocation:self.location andCompBlock:[self getSafetyTipsCompletion]];
    
}

-(arrayBlock)getSafetyTipsCompletion
{
    return ^(NSArray* array){
        safetyTips = array;
        [self.tableView reloadData];
    };
}
- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - UITableView delegates

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return safetyTips.count;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    return 150;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    static NSString* cellIdentifier = @"CellIdentifier";
    UITableViewCell* cell = [tableView dequeueReusableCellWithIdentifier:cellIdentifier];
    if (cell == nil) {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:cellIdentifier];
    }
    
    cell.textLabel.text = [safetyTips objectAtIndex:indexPath.row];
    cell.textLabel.lineBreakMode = NSLineBreakByWordWrapping;
    cell.textLabel.numberOfLines = 0;
    return cell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    
}

@end
