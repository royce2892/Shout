//
//  MainViewController.m
//  MyGola
//
//  Created by Ponnie Rohith on 25/07/15.
//  Copyright (c) 2015 PR. All rights reserved.
//

#import "MainViewController.h"
#import "ExploreViewController.h"
#import "ServiceChatsViewController.h"
#import "NameViewController.h"
#import "Message.h"
#import "PopoverViewController.h"
#import "WYPopoverController.h"

UIBarButtonItem *settings;
WYPopoverController *popOver;

@interface MainViewController () <UITextFieldDelegate ,WYPopoverControllerDelegate, PopOverTableDelegate>

@end
BOOL keyboardShown = NO;

@implementation MainViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.

    settings = [UIBarButtonItem new];
    settings.title = @"More";
    settings.tintColor = [UIColor whiteColor];
    settings.image = [UIImage imageNamed:@"settings"];
    self.navigationItem.rightBarButtonItem = settings;
    
    [settings setTarget:self];
    [settings setAction:@selector(settingsPopOver)];

    [self registerForKeyboardNotifs];
    [self.view bringSubviewToFront:self.placeTF];
    [self.placeTF becomeFirstResponder];
}

-(void)settingsPopOver
{
    PopoverViewController *PopoverView =[[PopoverViewController alloc]init];
    popOver = [[WYPopoverController alloc] initWithContentViewController:PopoverView];
    popOver.delegate = self;
    [popOver setPopoverContentSize:CGSizeMake(200, 150)];
    PopoverView.delegate = self;
    [popOver presentPopoverFromBarButtonItem:settings permittedArrowDirections:WYPopoverArrowDirectionUp animated:YES];
    //    [popOver presentPopoverFromRect:self.tableView.frame inView:self.view permittedArrowDirections:UIPopoverArrowDirectionUp animated:YES];
    
}
-(void)didSelectRowAtIndex:(NSUInteger)index
{
    [popOver dismissPopoverAnimated:YES];
    [AppDelegate sharedDelegate].setting = index;
}
- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
    [self unregisterKeyboardNotifs];
}
-(BOOL)textFieldShouldReturn:(UITextField *)textField
{
    [textField resignFirstResponder];
    return YES;
}
- (IBAction)exploreTapped:(id)sender {
    ExploreViewController *explore = [[ExploreViewController alloc]initWithLocation:self.placeTF.text];
    [self.navigationController pushViewController:explore animated:YES];
}

- (IBAction)shoutTapped:(id)sender {
    if (![self userNameEntered]) {
        NameViewController *nameVC = [NameViewController new];
        [self.navigationController pushViewController:nameVC animated:YES];
    }
    else{
        [self shout];
    }
}
-(void)shout
{
    Message *message = [[Message alloc]initWithText:@"shout" isSender:YES];
    [ParseDataHandler saveMessageInParse:message];
    [ParseDataHandler pushMessage:@"shout"];
    ServiceChatsViewController *chats = [ServiceChatsViewController new];
    [self.navigationController pushViewController:chats animated:YES];
  
}
- (BOOL)userNameEntered {
    if ([AppDelegate retrieveUserName]) {
        return YES;
    }
    return NO;
}

- (void)registerForKeyboardNotifs
{
    // register for keyboard notifications
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(keyboardWillShow:)
                                                 name:UIKeyboardWillShowNotification
                                               object:self.view.window];
    // register for keyboard notifications
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(keyboardWillHide:)
                                                 name:UIKeyboardWillHideNotification
                                               object:self.view.window];
    keyboardShown = NO;
    
}
- (void)keyboardWillHide:(NSNotification *)n
{
    NSDictionary* userInfo = [n userInfo];
    
    // get the size of the keyboard
    CGSize keyboardSize = [[userInfo objectForKey:UIKeyboardFrameBeginUserInfoKey] CGRectValue].size;
    
    
    // resize the scrollview
    CGRect viewFrame = self.view.frame;
    // I'm also subtracting a constant kTabBarHeight because my UIScrollView was offset by the UITabBar so really only the portion of the keyboard that is leftover pass the UITabBar is obscuring my UIScrollView.
    viewFrame.size.height += (keyboardSize.height);
    
    [UIView beginAnimations:nil context:NULL];
    [UIView setAnimationBeginsFromCurrentState:YES];
    [self.view setFrame:viewFrame];
    [UIView commitAnimations];
    
    keyboardShown = NO;
}

- (void)keyboardWillShow:(NSNotification *)n
{
    // This is an ivar I'm using to ensure that we do not do the frame size adjustment on the `UIScrollView` if the keyboard is already shown.  This can happen if the user, after fixing editing a `UITextField`, scrolls the resized `UIScrollView` to another `UITextField` and attempts to edit the next `UITextField`.  If we were to resize the `UIScrollView` again, it would be disastrous.  NOTE: The keyboard notification will fire even when the keyboard is already shown.
    if (keyboardShown) {
        return;
    }
    
    NSDictionary* userInfo = [n userInfo];
    
    // get the size of the keyboard
    CGSize keyboardSize = [[userInfo objectForKey:UIKeyboardFrameBeginUserInfoKey] CGRectValue].size;
    
    // resize the noteView
    CGRect viewFrame = self.view.frame;
    // I'm also subtracting a constant kTabBarHeight because my UIScrollView was offset by the UITabBar so really only the portion of the keyboard that is leftover pass the UITabBar is obscuring my UIScrollView.
    viewFrame.size.height -= (keyboardSize.height );
    
    [UIView beginAnimations:nil context:NULL];
    [UIView setAnimationBeginsFromCurrentState:YES];
    [self.view setFrame:viewFrame];
    [UIView commitAnimations];
    keyboardShown = YES;
}
-(void)unregisterKeyboardNotifs
{
    [[NSNotificationCenter defaultCenter] removeObserver:self name:UIKeyboardWillShowNotification object:nil];
    [[NSNotificationCenter defaultCenter] removeObserver:self name:UIKeyboardWillHideNotification object:nil];
}

@end
