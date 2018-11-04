/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 */

import React, {Component} from 'react';
import {Platform, StyleSheet, Text, View, Button, TextInput} from 'react-native';
import nodejs from 'nodejs-mobile-react-native';
import Bear from "./Bear";

const instructions = Platform.select({
  ios: 'Press Cmd+R to reload,\n' + 'Cmd+D or shake for dev menu',
  android:
    'Double tap R on your keyboard to reload,\n' +
    'Shake or press menu button for dev menu',
});

type Props = {};

export default class App extends Component<Props> {
  constructor(props) {
    super(props)
    this.state = {
      text: "",
    }
  }

  componentWillMount()
  {
    nodejs.start('main.js');
    nodejs.channel.addListener(
      'message',
      (msg) => {
        alert('From node: ' + msg);
      },
      this 
    );
  }

  handleSubmit() {
    nodejs.channel.post('analyze', this.state.text);
    this.refs.form.clear();
    this.setState({
      text: "",
    });
  }

  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>Welcome to React Native!</Text>
        <Text style={styles.instructions}>To get started, edit App.js</Text>
        <Text style={styles.instructions}>{instructions}</Text>
        <Button title="Message Node"
          onPress={() => nodejs.channel.send('A message!')}
        />
        <TextInput
          ref="form"
          style={{height: 40, width: 200}}
          placeholder="Echo"
          onChangeText={(text) => this.setState({text})}
          blurOnSubmit={true}
          onSubmitEditing={() => this.handleSubmit()}
        />
        <Bear></Bear>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#ffdb98',
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
