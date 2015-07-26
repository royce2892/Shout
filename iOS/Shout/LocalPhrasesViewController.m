//
//  LocalPhrasesViewController.m
//  TrackMyTrain
//
//  Created by Ponnie Rohith on 23/05/15.
//  Copyright (c) 2015 PR. All rights reserved.
//

#import "LocalPhrasesViewController.h"
#import "ParseDataHandler.h"


@interface LocalPhrasesViewController ()

@end

@implementation LocalPhrasesViewController

NSArray *localPhrases;
NSMutableArray *phrases;
NSMutableArray *localPhrasesDictionary;
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
    [ParseDataHandler getLocalPhrasesForLocation:self.location andCompBlock:[self getLocalPhrasesCompletion]];
    
}

-(arrayBlock)getLocalPhrasesCompletion
{
    return ^(NSArray* array){
//        phrases = [NSMutableArray new];
//        localPhrasesDictionary = [NSMutableArray new];
        localPhrases = array;
//        for (NSString *phrase in array) {
//            NSArray *comp = [phrase componentsSeparatedByString:@"~"];
//            [phrases addObject:comp[0]];
//            NSDictionary *dict = [NSDictionary new];
//            [dict setValue:comp[1] forKey:comp[0]];
//            [localPhrasesDictionary addObject:dict];
//        }
        [self.tableView reloadData];
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

#pragma mark - UITableView delegates

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return localPhrases.count;
}

//- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
//    return <#row height#>
//}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    static NSString* cellIdentifier = @"CellIdentifier";
    UITableViewCell* cell = [tableView dequeueReusableCellWithIdentifier:cellIdentifier];
    if (cell == nil) {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:cellIdentifier];
    }
    NSString *text = [localPhrases objectAtIndex:indexPath.row];
    cell.textLabel.text = [text stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceAndNewlineCharacterSet]];
//    cell.textLabel.text = text;
    return cell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    
}

@end
