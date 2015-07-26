//
//  ViewController.m
//  OlaAppathon
//
//  Created by Ponnie Rohith on 07/02/15.
//  Copyright (c) 2015 PR. All rights reserved.
//

#import "ViewController.h"
#import "SignUpViewController.h"
@interface ViewController ()

@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}
- (IBAction)signIn:(id)sender {

    [PFUser logInWithUsernameInBackground:self.username.text password:self.password.text block:^(PFUser *user, NSError *error) {
                         if (user) {
                            // Do stuff after successful login.
                             [self didSucceedLogin];
                             
                        } else {
                                            // The login failed. Check error to see why.
                            [self lockAnimationForView:self.password];
                            
                                }
    }];
    
}
-(void)lockAnimationForView:(UIView*)view
{
    CALayer *lbl = [view layer];
    CGPoint posLbl = [lbl position];
    CGPoint y = CGPointMake(posLbl.x-10, posLbl.y);
    CGPoint x = CGPointMake(posLbl.x+10, posLbl.y);
    CABasicAnimation * animation = [CABasicAnimation animationWithKeyPath:@"position"];
    [animation setTimingFunction:[CAMediaTimingFunction
                                  functionWithName:kCAMediaTimingFunctionEaseInEaseOut]];
    [animation setFromValue:[NSValue valueWithCGPoint:x]];
    [animation setToValue:[NSValue valueWithCGPoint:y]];
    [animation setAutoreverses:YES];
    [animation setDuration:0.08];
    [animation setRepeatCount:3];
    [lbl addAnimation:animation forKey:nil];
}
- (IBAction)signUp:(id)sender {
    
    SignUpViewController *signUpTVC = [SignUpViewController new];
    [self.navigationController pushViewController:signUpTVC animated:YES];
   
}

-(void)didSucceedLogin
{
    
}

@end
