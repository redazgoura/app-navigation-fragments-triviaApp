/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.navigation

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.android.navigation.databinding.FragmentGameWonBinding


class GameWonFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding: FragmentGameWonBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_game_won, container, false)

        binding.nextMatchButton.setOnClickListener { view: View ->
            view.findNavController().navigate(GameWonFragmentDirections.actionGameWonFragmentToGameFragment())
        }
        // to add menu to the action_bar
        setHasOptionsMenu(true)

        return binding.root
    }

    //to inflate the winner option menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        //inflates the menu res file named winner_menu into the menu parameter
        inflater?.inflate(R.menu.winner_menu, menu)

        //check if the intent will resolve to an activity
        // to do that we call resolveActivity with an instance of android system service (package-manager)
        //package-manager knows abt every activity that is  registered in the Android manifest across every app

        if(null == getShareIntent().resolveActivity(activity!!.packageManager)){

            menu?.findItem(R.id.share)?.setVisible(false)
        }
    }

    // Creating Share Intent
    private fun getShareIntent(): Intent {
        val args = GameWonFragmentArgs.fromBundle(arguments!!)

        //creating a new share_intent
         /*1-constructing the intent w/ ACTION_SEND to tell android
         we want activities that are registered w/
         an intent_filter to handle the send action */

        //method 1
       /* val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.setType("text/plain")  //setting the type of data we want to share
            .putExtra(Intent.EXTRA_TEXT, getString(R.string.share_success_text, args.numCorrect, args.numQuestions))
*/
        //method 2
        //generate the intent using ShareCompat class which uses the method chaining it makes the code more or less have the readability
        return ShareCompat.IntentBuilder.from(activity!!)
            .setText(getString(R.string.share_success_text, args.numCorrect, args.numQuestions))
            .setType("text/plain")
            .intent

    }

    // Starting an Activity with new Intent
    //method to do the sharing
    private fun shareSuccess() {
        startActivity(getShareIntent())
    }

    // hooking up share_success to our menu_item
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item!!.itemId){ // when the id matches R.id.share to find in the menu xml we call our shareSuccess()
            R.id.share -> shareSuccess()
        }
        return super.onOptionsItemSelected(item)
    }


}
