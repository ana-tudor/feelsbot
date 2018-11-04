import React from 'react';
import {
  AppRegistry,
  StyleSheet,
  Text,
  View,
  Button,
} from 'react-native';
import AnimatedSprite from 'react-native-animated-sprite';
import bearSprite from './assets/sprites/bear/bearSprite';

export default class Bear extends React.Component {
  constructor () {
    super();
    this.state = {
      animationType: 'WAVE',
      tweenOptions: {},
      animations: 4,
      animationIndex: 0,
    };
  }

  onPress () {
    var animationIndex = (this.state.animationIndex + 1) % this.state.animations;
    const animation = bearSprite.animationTypes[animationIndex];
    debugger;
    this.setState({ animationType: animation,
                    animationIndex: animationIndex });
  }

  tweenSprite () {
    const coords = this.refs.bearRef.getCoordinates();
    const location = [0, 0, 0];
    this.setState({
      tweenOptions: {
        tweenType: 'sine-wave',
        startXY: [coords.left, coords.top],
        xTo: [0, 0],
        yTo: [0, 0],
        duration: 1000,
        loop: false,
      }
    }, () => {
      this.refs.bearRef.startTween();
    });
  }

  render() {
    return (
        <AnimatedSprite
          ref={'bearRef'}
          sprite={bearSprite}
          animationFrameIndex={bearSprite.animationIndex(this.state.animationType)}
          loopAnimation={true}
          coordinates={{
            top: 500,
            right: 0,
            bottom: 0,
          }}
          size={{
            width: bearSprite.size.width * 1.65,
            height: bearSprite.size.height * 1.65,
          }}
          draggable={true}
          tweenOptions = {this.state.tweenOptions}
          tweenStart={'fromMethod'}
          onPress={() => {this.onPress();}}
        />
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
});

//AppRegistry.registerComponent('AnimatedSpriteExample', () => AnimatedSpriteExample);
