from flask import Flask, jsonify
from appv2_backend import init_spotify_client, get_top_tracks
from dotenv import load_dotenv
import os

# Load environment variables from .env
load_dotenv()

app = Flask(__name__)

# Endpoint to get user's top 10 tracks with artists and genres
@app.route('/api/user/top-tracks', methods=['GET'])
def user_top_tracks():
    print("Received request for user's top tracks")
    
    # Initialize Spotipy client
    sp = init_spotify_client()

    # Get top 10 tracks with artists and genres
    top_tracks = get_top_tracks(sp)

    # Return the top tracks as JSON
    return jsonify(top_tracks)

if __name__ == '__main__':
    # Run the Flask app on port 8888
    app.run(debug=True, port=8888)
